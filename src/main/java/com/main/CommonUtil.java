package com.main;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CommonUtil {

	private CommonUtil() {

	}

	public static String base64Decode(String content) {
		return new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8);
	}

	public static String base64Encode(String content) {
		return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
	}

}
