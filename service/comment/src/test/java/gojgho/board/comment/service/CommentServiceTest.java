package gojgho.board.comment.service;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import gojgho.board.comment.entity.Comment;
import gojgho.board.comment.repository.CommentRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

  @InjectMocks
  CommentService commentService;

  @Mock
  CommentRepository commentRepository;

  @Test
  @DisplayName("삭제할 댓글이 자식이 있음면, 삭제 표시만 한다.")
  void deleteShouldMarkDeletedIfHasChildren() {
    //given
    Long articleId = 1l;
    Long commentId = 2L;

    Comment comment = createComment(articleId, commentId);
    given(commentRepository.findById(commentId))
        .willReturn(Optional.of(comment));
    given(commentRepository.countBy(articleId, commentId, 2L))
        .willReturn(2L);

    //when
    commentService.deleteId(commentId);

    //then
    verify(comment).delete();
  }

  @Test
  @DisplayName("하위 댓글이 삭제되고. 삭제되지 않은 부모면, 하위 댓글만 삭제한다")
  void deleteShouldDeleteChildOnlyIfNotDeletedParent() {
    //given
    Long articleId = 1l;
    Long commentId = 2L;
    Long parentCommentId = 1L;

    Comment comment = createComment(articleId, commentId, parentCommentId);
    given(comment.isRoot()).willReturn(false);

    Comment parent = mock(Comment.class);
    given(parent.getDeleted()).willReturn(false);

    given(commentRepository.findById(commentId))
        .willReturn(Optional.of(comment));
    given(commentRepository.countBy(articleId, commentId, 2L))
        .willReturn(1L);

    given(commentRepository.findById(parentCommentId))
        .willReturn(Optional.of(parent));
    //when
    commentService.deleteId(commentId);

    //then
    verify(commentRepository).delete(comment);
    verify(commentRepository, never()).delete(parent);
  }

  @Test
  @DisplayName("하위 댓글이 삭제되고. 삭제된 부모면, 재귀적으로 모두 삭제한다.")
  void deleteShouldDeleteAllRecursivelyIfDeletedParent() {
    //given
    Long articleId = 1l;
    Long commentId = 2L;
    Long parentCommentId = 1L;

    Comment comment = createComment(articleId, commentId, parentCommentId);
    given(comment.isRoot()).willReturn(false);

    Comment parent = createComment(articleId, parentCommentId);
    given(parent.isRoot()).willReturn(true);
    given(parent.getDeleted()).willReturn(true);

    given(commentRepository.findById(commentId))
        .willReturn(Optional.of(comment));
    given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(1L);

    given(commentRepository.findById(parentCommentId))
        .willReturn(Optional.of(parent));
    given(commentRepository.countBy(articleId, parentCommentId, 2L)).willReturn(1L);

    //when
    commentService.deleteId(commentId);

    //then
    verify(commentRepository).delete(comment);
    verify(commentRepository).delete(parent);
  }

  private Comment createComment(Long articleId, Long commentId) {
    Comment comment = mock(Comment.class);
    given(comment.getArticleId()).willReturn(articleId);
    given(comment.getCommentId()).willReturn(commentId);
    return comment;
  }

  private Comment createComment(Long articleId, Long commentId, Long parentCommentId) {
    Comment comment = createComment(articleId, commentId);
    given(comment.getParentCommentId()).willReturn(parentCommentId);
    return comment;
  }
}
