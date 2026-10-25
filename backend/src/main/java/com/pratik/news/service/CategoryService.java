package com.pratik.news.service;

import com.pratik.news.entity.Category;
import com.pratik.news.exception.BadRequestException;
import com.pratik.news.exception.ResourceNotFoundException;
import com.pratik.news.repository.ArticleRepository;
import com.pratik.news.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public CategoryService(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll(Sort.by("name"));
    }

    public Category get(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    public Category create(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName().trim())) {
            throw new BadRequestException("Category '" + category.getName() + "' already exists");
        }
        category.setId(null);
        category.setName(category.getName().trim());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = get(id);
        if (articleRepository.existsByCategoryId(id)) {
            throw new BadRequestException("Cannot delete a category that still has articles");
        }
        categoryRepository.delete(category);
    }
}
