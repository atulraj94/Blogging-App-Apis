package com.atul.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atul.blog.entities.Comment;
import com.atul.blog.entities.Post;
import com.atul.blog.exceptions.ResourseNotFoundException;
import com.atul.blog.payloads.CommentDto;
import com.atul.blog.repositories.CommentRepo;
import com.atul.blog.repositories.PostRepo;
import com.atul.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> 
		                      new ResourseNotFoundException("Post", "Post Id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> 
		                      new ResourseNotFoundException("Comment", "Comment Id", commentId));
		
		this.commentRepo.delete(comment);
	}

}
