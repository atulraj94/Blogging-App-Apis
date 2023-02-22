package com.atul.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atul.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
