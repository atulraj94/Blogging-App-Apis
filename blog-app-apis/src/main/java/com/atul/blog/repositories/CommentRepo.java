package com.atul.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atul.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	
	

}
