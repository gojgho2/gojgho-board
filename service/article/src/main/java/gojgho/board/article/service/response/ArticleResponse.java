package gojgho.board.article.service.response;

import gojgho.board.article.entity.Article;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleResponse {

  private Long articleId;
  private String title;
  private String content;
  private Long boardId;
  private Long writeId;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public static ArticleResponse from(Article article) {
    ArticleResponse response = new ArticleResponse();
    response.articleId = article.getArticleId();
    response.title = article.getTitle();
    response.content = article.getContent();
    response.boardId = article.getBoardId();
    response.writeId = article.getWriterId();
    response.createdAt = article.getCreatedAt();
    response.modifiedAt = article.getModifiedAt();
    return response;
  }
}
