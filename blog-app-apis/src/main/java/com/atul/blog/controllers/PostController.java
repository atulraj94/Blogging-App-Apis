package com.atul.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.atul.blog.config.AppConstant;
import com.atul.blog.payloads.ApiResponse;
import com.atul.blog.payloads.PostDto;
import com.atul.blog.payloads.PostResponse;
import com.atul.blog.services.FileService;
import com.atul.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postservice;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
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
				           @RequestParam(value="pageNumber", defaultValue=AppConstant.PAGE_NUMBER ,required=false) Integer pageNumber,
				           @RequestParam(value="pageSize",defaultValue=AppConstant.PAGE_SIZE,required= false) Integer pageSize,
				           @RequestParam(value="sortBy",defaultValue=AppConstant.SORT_BY,required=false) String sortBy){
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
		
		
	//search by title
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable ("keywords") String keywords) {
			
			List<PostDto> postDto = this.postservice.searchposts(keywords);
			return new ResponseEntity<List<PostDto>>(postDto, HttpStatus.OK);
			
		}
		
		
	//	post image upload
		@PostMapping("post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(
				@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId) throws IOException
		{
			PostDto postDto = this.postservice.getPostById(postId);
			String fileName = this.fileService.uploadImage(path, image);
			postDto.setImageName(fileName);
			PostDto updatePost = this.postservice.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
		}
		
		
		@GetMapping("/post/image/{imageName}")
		public void downloadImage(
				@PathVariable ("imageName") String imageName,
				HttpServletResponse response
				) throws IOException {
			
			InputStream resourse = this.fileService.getResourse(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resourse, response.getOutputStream());
		}
		
		

}
