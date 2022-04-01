package com.shopme.admin.users;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "notEncryptedPass";
		String encodedPass = passwordEncoder.encode(rawPassword);
		System.out.println(encodedPass);
		assertTrue(passwordEncoder.matches(rawPassword, encodedPass));
	}

}
