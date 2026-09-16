package com.wisdomtechinc.drwisetodospringboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtServiceTest {

	private JwtService jwtService;

	private String secret;

	@BeforeEach
	void setUp() {
		secret = "Zeg4BVlqPWPKtMn1IMFWXl77bPf2vKHG9aXB5u0eGRk=";
		jwtService = new JwtService(secret, 3600);
	}

	@Test
	@DisplayName("Should successfully generate valid JWT token")
	void testGenerateToken() {
		String username = "julius";
		String generatedToken = jwtService.generateToken(username);

		assertNotNull(generatedToken, "Generated token should not be null");

		// verify it looks like a 3-part JWT header.payload.signature
		assertEquals(3, generatedToken.split("\\.").length);
	}

	@Test
	@DisplayName("Should successfully extract the correct username from a valid token")
	void testExtractUsername() {
		String username = "julius";
		String generatedToken = jwtService.generateToken(username);

		String extractedUsername = jwtService.extractUsername(generatedToken);

		assertEquals(username, extractedUsername, "Extracted username must match the subject.");
	}

	@Test
	@DisplayName("Should throw ExpiredJwtException when the token is past its expiry")
	void testExtractUsername_ExpiredToken_ThrowsException() {
		long expirationSeconds = -60L;
		String username = "julius";

		JwtService expiredJwtService = new JwtService(secret, expirationSeconds);

		String token = expiredJwtService.generateToken(username);
		assertThrows(ExpiredJwtException.class, () -> {
			expiredJwtService.extractUsername(token);
		}, "Should reject token since its expiration date has passed");
	}

}
