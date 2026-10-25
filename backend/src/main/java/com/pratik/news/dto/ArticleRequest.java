package com.pratik.news.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArticleRequest(
        @NotBlank(message = "Title is required") @Size(max = 255) String title,
        @Size(max = 500, message = "Summary can be at most 500 characters") String summary,
        @NotBlank(message = "Content is required") String content,
        String imageUrl,
        @NotBlank(message = "Author is required") String author,
        @NotNull(message = "Category is required") Long categoryId,
        boolean featured
) {
}
