package com.viet.freelance.service.impl;

import com.viet.freelance.entity.CategoryEntity;
import com.viet.freelance.io.request.CategoryRequest;
import com.viet.freelance.io.response.CategoryResponse;
import com.viet.freelance.repository.CategoryRepository;
import com.viet.freelance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse add(CategoryRequest request) {
        CategoryEntity newCategory = convertToCategory(request);
        categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }
    private CategoryEntity convertToCategory(CategoryRequest request){
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
    private CategoryResponse convertToResponse(CategoryEntity categoryEntity){
        return CategoryResponse.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .bgColor(categoryEntity.getBgColor())
                .imgUrl(categoryEntity.getImgUrl())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .build();
    }
}
