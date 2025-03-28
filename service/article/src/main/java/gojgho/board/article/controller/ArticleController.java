package gojgho.board.article.controller;

import gojgho.board.article.service.ArticleService;
import gojgho.board.article.service.request.ArticleCreateRequest;
import gojgho.board.article.service.request.ArticleUpdateRequest;
import gojgho.board.article.service.response.ArticlePageResponse;
import gojgho.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;


  @GetMapping("/v1/articles/{articleId}")
  public ArticleResponse read(@PathVariable Long articleId) {
    return articleService.read(articleId);
  }

  @GetMapping("/v1/articles")
  public ArticlePageResponse readAll(
          @RequestParam("boardId") Long boardId,
          @RequestParam("page") Long page,
          @RequestParam("pageSize") Long pageSize
  ) {
    return articleService.readAll(boardId, page, pageSize);
  }

  @PostMapping("/v1/articles")
  public ArticleResponse create(@RequestBody ArticleCreateRequest request) {
    return articleService.create(request);
  }

  @PutMapping("/v1/articles/{articleId}")
  public ArticleResponse update(@PathVariable Long articleId, @RequestBody ArticleUpdateRequest request) {
    return articleService.update(articleId, request);
  }


  @DeleteMapping("/v1/articles/{articleId}")
  public void update(@PathVariable Long articleId) {
    articleService.delete(articleId);
  }
}
