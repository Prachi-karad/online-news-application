package com.prachi.news.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String summary;

    @Lob
    @Column(nullable = false)
    private String content;

    private String imageUrl;

    @Column(nullable = false)
    private String author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean featured;

    private long views;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        publishedAt = LocalDateTime.now();
        updatedAt = publishedAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }
    public long getViews() { return views; }
    public void setViews(long views) { this.views = views; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
