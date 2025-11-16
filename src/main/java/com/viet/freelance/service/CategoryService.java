package com.viet.freelance.service;

import com.viet.freelance.io.request.CategoryRequest;
import com.viet.freelance.io.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse add(CategoryRequest request);
}
