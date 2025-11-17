package com.viet.freelance.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viet.freelance.io.request.CategoryRequest;
import com.viet.freelance.io.response.CategoryResponse;
import com.viet.freelance.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString,
                                        @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest request = null;
        try {
            request = objectMapper.readValue(categoryString, CategoryRequest.class);
            return categoryService.add(request, file);
        }catch (JsonProcessingException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occred while the json: " + ex.getMessage());
        }

    }
    @GetMapping
    public List<CategoryResponse> fetchCategories(){
        return categoryService.read();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{categoryId}")
    public void remove(@PathVariable String categoryId){
        try {
            categoryService.delete(categoryId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
