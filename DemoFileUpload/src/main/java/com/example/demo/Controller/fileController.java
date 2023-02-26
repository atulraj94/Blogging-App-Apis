package com.example.demo.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Payload.FileResponse;
import com.example.demo.Service.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class fileController {
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image){
		
		String fileName = null;
		try {
			fileName = this.fileService.uploadImage(path, image);
		}
		catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null,"Image not uploaded"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<FileResponse>(new FileResponse(fileName,"Image uploaded successfully"), HttpStatus.OK);
		
		
	}
	
	
	//method to serve file
	@GetMapping("/image/{imageName}")
	public void downloadImage(
			@PathVariable ("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		
		InputStream resourse = this.fileService.getResourse(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resourse, response.getOutputStream());
	}
	
	
	
	
	
	

}
