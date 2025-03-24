package gojgho.board.comment.api;

import gojgho.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {


  RestClient restClient = RestClient.create("http://localhost:9001");

  @Test
  void create() {
    CommentResponse response1 = createComment(
        new CommentCreateRequest(1l, "my comment1", null, 1l));
    CommentResponse response2 = createComment(
        new CommentCreateRequest(1l, "my comment2", response1.getCommentId(), 1l));
    CommentResponse response3 = createComment(
        new CommentCreateRequest(1l, "my comment3", response1.getCommentId(), 1l));

    System.out.println("commentId = %s".formatted(response1.getCommentId()));
    System.out.println("commentId = %s".formatted(response2.getCommentId()));
    System.out.println("commentId = %s".formatted(response3.getCommentId()));



  }

  CommentResponse createComment(CommentCreateRequest request) {
    return restClient.post()
        .uri("/v1/comments")
        .body(request)
        .retrieve()
        .body(CommentResponse.class);
  }


  @Test
  void read() {
    CommentResponse response = restClient.get()
        .uri("/v1/comments/{commentId}", 162387724723707904l)
        .retrieve()
        .body(CommentResponse.class);

    System.out.printf("response = %s%n", response);
  }

  @Test
  void delete() {
    //    commentId = 162387724723707904l
     //    commentId = 162387725487071232l
     //    commentId = 162387725621288960l

    restClient.delete()
        .uri("/v1/comments/{commentId}", 162387725621288960l)
        .retrieve();
  }


  @Getter
  @AllArgsConstructor
  public static class CommentCreateRequest {
    private Long articleId;
    private String content;
    private Long parentCommentId;
    private Long writerId;
  }

}
