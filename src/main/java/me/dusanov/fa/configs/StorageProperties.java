package me.dusanov.fa.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "upload")
public class StorageProperties {
	
	@Getter @Setter private String path;

}
