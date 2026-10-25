package com.pratik.news.controller;

import com.pratik.news.dto.ArticleRequest;
import com.pratik.news.dto.CommentRequest;
import com.pratik.news.dto.PageResponse;
import com.pratik.news.entity.Article;
import com.pratik.news.entity.Comment;
import com.pratik.news.service.ArticleService;
import com.pratik.news.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    /** GET /api/articles?categoryId=1&search=india&page=0&size=9 */
    @GetMapping
    public PageResponse<Article> search(@RequestParam(required = false) Long categoryId,
                                        @RequestParam(required = false) String search,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "9") int size) {
        return articleService.search(categoryId, search, page, size);
    }

    @GetMapping("/featured")
    public List<Article> featured() {
        return articleService.getFeatured();
    }

    @GetMapping("/trending")
    public List<Article> trending() {
        return articleService.getTrending();
    }

    /** Reading an article increases its view count. Pass ?count=false to skip that (e.g. admin edit). */
    @GetMapping("/{id}")
    public Article get(@PathVariable Long id, @RequestParam(defaultValue = "true") boolean count) {
        return count ? articleService.read(id) : articleService.get(id);
    }

    @PostMapping
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.create(request));
    }

    @PutMapping("/{id}")
    public Article update(@PathVariable Long id, @Valid @RequestBody ArticleRequest request) {
        return articleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comments")
    public List<Comment> comments(@PathVariable Long id) {
        return commentService.getForArticle(id);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.add(id, request));
    }
}
