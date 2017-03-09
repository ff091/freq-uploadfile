package com.fsoft.cme.ocr.utils;

public class StringUtils {

	public static String normalizeFileName(String fileName){
		if (fileName != null && fileName.length() > 0 && fileName.lastIndexOf(".") > 0){
			String extension = fileName.substring(fileName.lastIndexOf("."));
			String name = fileName.substring(0, fileName.lastIndexOf("."));
			name = name.replaceAll("[^a-zA-Z0-9]+","");
			return name + extension;
		}
		return "";
	}
	
	
}
