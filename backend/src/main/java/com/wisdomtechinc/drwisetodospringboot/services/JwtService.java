package com.wisdomtechinc.drwisetodospringboot.services;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final SecretKey key;

	private final long expirationSeconds;

	public JwtService(@Value("${jwt.secret}") String secret,
			@Value("${jwt.expiration-seconds}") long expirationSeconds) {

		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		this.expirationSeconds = expirationSeconds;
	}

	public String generateToken(String username) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationSeconds * 1000);

		return Jwts.builder().subject(username).issuedAt(now).expiration(expiry).signWith(key).compact();
	}

	public String extractUsername(String token) {
		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

		return claims.getSubject();
	}

}
