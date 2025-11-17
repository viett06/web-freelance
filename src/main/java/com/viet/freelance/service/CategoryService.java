package com.viet.freelance.service;

import com.viet.freelance.io.request.CategoryRequest;
import com.viet.freelance.io.response.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryResponse add(CategoryRequest request, MultipartFile file);
    List<CategoryResponse> read();
    void delete(String categoryId);
}
