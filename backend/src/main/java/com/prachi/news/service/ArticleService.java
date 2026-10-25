package com.prachi.news.service;

import com.prachi.news.dto.ArticleRequest;
import com.prachi.news.dto.PageResponse;
import com.prachi.news.entity.Article;
import com.prachi.news.exception.ResourceNotFoundException;
import com.prachi.news.repository.ArticleRepository;
import com.prachi.news.repository.CommentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private static final int MAX_PAGE_SIZE = 50;

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final CategoryService categoryService;

    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository,
                          CategoryService categoryService) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.categoryService = categoryService;
    }

    public PageResponse<Article> search(Long categoryId, String keyword, int page, int size) {
        String q = (keyword == null || keyword.isBlank()) ? null : keyword.trim();
        PageRequest pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), MAX_PAGE_SIZE),
                Sort.by(Sort.Direction.DESC, "publishedAt"));
        return PageResponse.of(articleRepository.search(categoryId, q, pageable));
    }

    public List<Article> getFeatured() {
        return articleRepository.findTop5ByFeaturedTrueOrderByPublishedAtDesc();
    }

    public List<Article> getTrending() {
        return articleRepository.findTop5ByOrderByViewsDesc();
    }

    public Article get(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
    }

    /** Returns an article and counts one view. */
    @Transactional
    public Article read(Long id) {
        if (articleRepository.incrementViews(id) == 0) {
            throw new ResourceNotFoundException("Article not found with id " + id);
        }
        return get(id);
    }

    public Article create(ArticleRequest request) {
        Article article = new Article();
        apply(article, request);
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleRequest request) {
        Article article = get(id);
        apply(article, request);
        return articleRepository.save(article);
    }

    @Transactional
    public void delete(Long id) {
        Article article = get(id);
        commentRepository.deleteByArticleId(id);
        articleRepository.delete(article);
    }

    private void apply(Article article, ArticleRequest r) {
        article.setTitle(r.title().trim());
        article.setSummary(r.summary());
        article.setContent(r.content());
        article.setImageUrl(r.imageUrl());
        article.setAuthor(r.author().trim());
        article.setCategory(categoryService.get(r.categoryId()));
        article.setFeatured(r.featured());
    }
}
