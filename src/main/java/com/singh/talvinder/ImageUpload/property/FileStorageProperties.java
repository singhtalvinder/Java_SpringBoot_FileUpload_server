package com.singh.talvinder.ImageUpload.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix= "file")
public class FileStorageProperties {
	private String uploadDirectory;
	
	public String getUploadDirectory() {
		return "c:/uploads";//uploadDirectory;
	}

	public void setUploadDirectory(String uploadDirectory) {
		this.uploadDirectory = "c:/uploads";//uploadDirectory;
	}

}
