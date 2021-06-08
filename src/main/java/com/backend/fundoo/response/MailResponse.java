package com.backend.fundoo.response;

public class MailResponse {

	public static String formMessage(String url, String token) {
		return url + "/" + token;
	}
}
