package com.fsoft.cme.ocr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fsoft.cme.ocr.controller.FileUploadController;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartup.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			Files.createDirectory(Paths.get(FileUploadController.ROOT));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return;
	}

}
