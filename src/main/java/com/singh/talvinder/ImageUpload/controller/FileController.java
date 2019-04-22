package com.singh.talvinder.ImageUpload.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//import com.singh.talvinder.ImageUpload.exception.MyFileNotFoundException;
//import com.singh.talvinder.ImageUpload.exception.MyFileNotFoundException;
import com.singh.talvinder.ImageUpload.payload.UploadFileResponse;
import com.singh.talvinder.ImageUpload.service.FileStorageService;
@CrossOrigin//(origins="http://localhost:8080")
@RestController
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	//@CrossOrigin(origins="http://localhost:8080")
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
		
		return new UploadFileResponse(
				fileName, 
				fileDownloadUri, 
				file.getContentType(),
				file.getSize());
		
	}
	
	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
	
	/*
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){ 
		// throws MyFileNotFoundException {
		// Load the file as resource
		Resource resource;
		// Determine file content type
		String contentType = null;
		try {
			resource = fileStorageService.loadFileAsResource(fileName);
	

		//try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (MyFileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		} catch(IOException e) {
			logger.info("Failed to get the file type!");
		}
		
		// Fallback to default content type if the type could not be 
		// determined.
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}*/
}
