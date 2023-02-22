package com.atul.blog.services;

import java.util.List;

import com.atul.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//read all
	List<CategoryDto> getCategories();
	
	//read by id
	CategoryDto getCategory(Integer categoryId);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
    void deleteCategory(Integer categoryId);
}
