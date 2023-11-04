package com.ms.emailconfirmation.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class CommonUtil {

	public static String generateRandomString(int length) {

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder randomString = new StringBuilder(length);
		SecureRandom random = new SecureRandom();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			randomString.append(characters.charAt(index));
		}

		return randomString.toString();
	}

	public static String readTemplate(ResourceLoader resourceLoader, String templateName) throws Exception {
		Resource resource = resourceLoader.getResource("classpath:templates/" + templateName);
		String htmlContent = null;
		try {
			byte[] bytes = null;
			bytes = Files.readAllBytes(Paths.get(resource.getURI()));

			htmlContent = new String(bytes);

		} catch (Exception e) {
			throw e;
		}
		return htmlContent;
	}
}
