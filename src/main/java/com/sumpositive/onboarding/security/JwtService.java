
package com.sumpositive.onboarding.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

import com.sumpositive.onboarding.entity.User;

@Service
public class JwtService {

	@Value("${app.jwt.secret}")
	private String secret;

	@Value("${app.jwt.expiration}")
	private long expiration;

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Generate JWT token with user role
	public String generateToken(User user) {

		Date now = new Date();
		Date expiry = new Date(now.getTime() + expiration);

		return Jwts.builder().subject(user.getEmail()).claim("role", user.getRole().name()).issuedAt(now)
				.expiration(expiry).signWith(getSigningKey()).compact();
	}

	// Extract email
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}

	// Extract role
	public String extractRole(String token) {
		return extractAllClaims(token).get("role", String.class);
	}

	// Validate JWT token
	public boolean isTokenValid(String token, String email) {

		Claims claims = extractAllClaims(token);

		return claims.getSubject().equals(email) && claims.getExpiration().after(new Date());
	}

	public long getExpiration() {
		return expiration;
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
}
