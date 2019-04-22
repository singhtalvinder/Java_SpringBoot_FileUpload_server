package com.singh.talvinder.ImageUpload;


import com.singh.talvinder.ImageUpload.property.FileStorageProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class 
})

public class ImageUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageUploadApplication.class, args);
	}

}
