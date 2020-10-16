package me.dusanov.fa.services;

import java.io.IOException;
import java.io.InputStream;
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

import me.dusanov.fa.configs.StorageProperties;

@Service
public class FileSystemStorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getPath());
	}
	
	public String store(MultipartFile file) throws Exception {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new Exception("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new Exception(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
			}
			
			return filename;
		}
		catch (IOException e) {
			throw new Exception("Failed to store file " + filename, e);
		}
	}	
	
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}
	
	public Resource loadAsResource(String filename) throws Exception {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new Exception("Could not read file: " + filename);
			}
		}
		catch (MalformedURLException e) {
			throw new Exception("Could not read file: " + filename, e);
		}
	}

}
