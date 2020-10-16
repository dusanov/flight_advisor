package me.dusanov.fa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.dusanov.fa.configs.StorageProperties;

@Component
public class StartupComponent implements CommandLineRunner {

	private final StorageProperties storageProps;
	
	public StartupComponent (StorageProperties storageProps){
		this.storageProps = storageProps;
	}
	
	@Override
	public void run(String... args) throws Exception {
		String path = storageProps.getPath();
		
		try {
			Files.createDirectories(Paths.get(path));
		}
		catch (IOException e) {
			throw new Exception("Could not initialize storage", e);
		}		

	}

}
