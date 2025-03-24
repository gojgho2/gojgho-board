package gojgho.board.comment.service.response;

import gojgho.board.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentResponse {

  private Long commentId;

  private String content;

  private Long parentCommentId;

  private Long articleId;

  private Long writerId;

  private Boolean deleted;

  private LocalDateTime createdAt;

  public static CommentResponse from(Comment comment) {
    CommentResponse response = new CommentResponse();

    response.commentId = comment.getCommentId();
    response.content = comment.getContent();
    response.parentCommentId = comment.getParentCommentId();
    response.articleId = comment.getArticleId();
    response.writerId = comment.getWriterId();
    response.createdAt = comment.getCreatedAt();
    response.deleted = comment.getDeleted();

    return response;
  }
}
