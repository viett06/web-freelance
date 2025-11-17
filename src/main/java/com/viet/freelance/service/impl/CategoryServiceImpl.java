package com.viet.freelance.service.impl;

import com.viet.freelance.entity.CategoryEntity;
import com.viet.freelance.io.request.CategoryRequest;
import com.viet.freelance.io.response.CategoryResponse;
import com.viet.freelance.repository.CategoryRepository;
import com.viet.freelance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final  FileUploadServiceImpl fileUploadService;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToCategory(request);
        newCategory.setImgUrl(imgUrl);
        categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
                        .orElseThrow(()-> new RuntimeException("Category not found" + categoryId));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepository.delete(existingCategory);
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
