package gojgho.board.article.repository;

import static org.junit.jupiter.api.Assertions.*;

import gojgho.board.article.entity.Article;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {


  @Autowired
  ArticleRepository articleRepository;

  @Test
  void findAllTest() {
    List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);
    log.info("articles.size = {}", articles.size());

    for(Article article : articles) {
      log.info("article = {}", article);
    }
  }

  @Test
  void countTest() {
    Long count = articleRepository.count(1L, 10000L);
    log.info("count = {}", count);
  }
}
