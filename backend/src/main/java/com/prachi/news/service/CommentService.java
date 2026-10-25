package com.prachi.news.service;

import com.prachi.news.dto.CommentRequest;
import com.prachi.news.entity.Comment;
import com.prachi.news.exception.ResourceNotFoundException;
import com.prachi.news.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    public CommentService(CommentRepository commentRepository, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
    }

    public List<Comment> getForArticle(Long articleId) {
        articleService.get(articleId);
        return commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId);
    }

    public Comment add(Long articleId, CommentRequest request) {
        Comment comment = new Comment();
        comment.setArticle(articleService.get(articleId));
        comment.setName(request.name().trim());
        comment.setText(request.text().trim());
        return commentRepository.save(comment);
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        commentRepository.delete(comment);
    }
}
