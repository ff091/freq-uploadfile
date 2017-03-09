package com.fsoft.cme.ocr.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.cme.ocr.utils.StringUtils;

@Controller
public class FileUploadController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
	
	public static final String ROOT = "upload-dir";
	
	@Value("${image.url}")
	private String imageUrl;
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadFile")
	@ResponseBody
	public URLResponse handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		long start = System.currentTimeMillis();

		try {
			if (!file.isEmpty()) {
				String saveFileName = start + "_" + StringUtils.normalizeFileName(file.getOriginalFilename());
				Files.copy(file.getInputStream(), Paths.get(ROOT, saveFileName));
				return new URLResponse(imageUrl + saveFileName);
			}
		}  catch (Exception e) {
			LOGGER.error("System error ", e);
		}
		LOGGER.info("Upload time time: " + (System.currentTimeMillis() - start) + " ms.");
		return null;
	}
	
	/*@RequestMapping(method = RequestMethod.POST, value = "/uploadFile")
	@ResponseBody
	public URLResponse handleJsonUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		long start = System.currentTimeMillis();

		try {
			if (!file.isEmpty()) {
				String saveFileName = start + "_" + StringUtils.normalizeFileName(file.getOriginalFilename());
				Files.copy(file.getInputStream(), Paths.get(ROOT, saveFileName));
				return new URLResponse(imageUrl + saveFileName);
			}
		}  catch (Exception e) {
			LOGGER.error("System error ", e);
		}
		LOGGER.info("Upload time time: " + (System.currentTimeMillis() - start) + " ms.");
		return null;
	}*/

	
	@RequestMapping(method = RequestMethod.GET, value = "/image")
	public @ResponseBody ResponseEntity<byte[]> image(@RequestParam(value = "img") String img) throws Exception {
		File file = new File(Paths.get(ROOT, img).toAbsolutePath().toString());

	    InputStream in = new FileInputStream(file);

	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    
	    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public @ResponseBody String test() {
		return "test";
	}
	
	private class URLResponse {
		private String url;
		
		public URLResponse(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
	}
}
