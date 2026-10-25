package com.prachi.news.repository;

import com.prachi.news.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    /** Filter by optional category and optional keyword (title or summary). */
    @Query("""
            SELECT a FROM Article a
            WHERE (:categoryId IS NULL OR a.category.id = :categoryId)
              AND (:keyword IS NULL
                   OR LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(a.summary) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<Article> search(@Param("categoryId") Long categoryId,
                         @Param("keyword") String keyword,
                         Pageable pageable);

    List<Article> findTop5ByFeaturedTrueOrderByPublishedAtDesc();

    List<Article> findTop5ByOrderByViewsDesc();

    boolean existsByCategoryId(Long categoryId);

    @Modifying
    @Query("UPDATE Article a SET a.views = a.views + 1 WHERE a.id = :id")
    int incrementViews(@Param("id") Long id);
}
