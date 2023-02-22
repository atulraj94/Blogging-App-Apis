package com.atul.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atul.blog.payloads.ApiResponse;
import com.atul.blog.payloads.PostDto;
import com.atul.blog.payloads.PostResponse;
import com.atul.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postservice;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable Integer userId, 
			@PathVariable Integer categoryId)
	{
		PostDto createPost = this.postservice.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.postservice.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK); 
	}
	
	//get by category
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
			
			List<PostDto> posts = this.postservice.getPostsByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK); 
		}
		
		
	//get all post
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPost(
				           @RequestParam(value="pageNumber", defaultValue="0",required=false) Integer pageNumber,
				           @RequestParam(value="pageSize",defaultValue="5",required= false) Integer pageSize,
				           @RequestParam(value="sortBy",defaultValue="postId",required=false) String sortBy){
			PostResponse response = this.postservice.getAllPost(pageNumber, pageSize,sortBy);
			return new ResponseEntity<PostResponse>(response, HttpStatus.OK);
		}
		
		
	//get post by id
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
			PostDto postDto = this.postservice.getPostById(postId);
			return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
			
		}
		
	//delete post
		@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePost(@PathVariable Integer postId) {
			this.postservice.deletePost(postId);
			return new ApiResponse("post deleted successfully", true);
		}
		
		
	//update post
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
			PostDto updatedpostDto = this.postservice.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedpostDto, HttpStatus.OK);
			
		}
		
		
		
		
		
		
		
		
		

}
