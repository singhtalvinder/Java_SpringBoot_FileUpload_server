package com.singh.talvinder.ImageUpload.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.singh.talvinder.ImageUpload.exception.FileStorageException;
import com.singh.talvinder.ImageUpload.exception.MyFileNotFoundException;
import com.singh.talvinder.ImageUpload.property.FileStorageProperties;

@Service
public class FileStorageService {
	private  Path fileStorageLocation; //final
	
	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDirectory())
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch(Exception e) {
			throw new FileStorageException("Failed to create directory to store uploaded files", e);
		}
	}
	
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			 // Check filename for invalid chars.
			if(fileName.contains("..")) {
				throw new FileStorageException("Invalid path sequence encountered in the filename: " + fileName);
			}
			
			// copy file to target location.
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			long copy = Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
			
		} catch(IOException ioE) {
			throw new FileStorageException("Failed to store file: " + fileName + "Please retry...", ioE);
		}
	}
	
	public Resource loadFileAsResource(String fileName) throws MyFileNotFoundException {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			}
			else {
				throw new MyFileNotFoundException("File not found: " + fileName);
			}
		} catch(MalformedURLException muE) {
			throw new MyFileNotFoundException("File not found: " + fileName, muE);
		}
	}

}
