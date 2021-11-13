package com.cos.email_test.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

	public static String encrypt(String rawPassword, MyAlgorithm algorithm) {
		// SHA256 함수를 가진 객체 가지고 오기
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance(algorithm.getType());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// 비밀번호 -> SHA256
		md.update(rawPassword.getBytes());
		
		// 암호화된 글자를 16진수로 변환
		StringBuilder sb = new StringBuilder();
		for (Byte b : md.digest()) {
			sb.append(String.format("%02x", b)); // 16진수로 바꿈, SHA-256의 프로토콜
		}
		
		return sb.toString();
		
	}
}
