package com.reddit.reddit_clone.service;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
	String extractUsername(String token);

	<T> T extractClaim(String token, Function<Claims, T> claimResolver);

	String generateToken(UserDetails userDetails);

	boolean isTokenValid(String token, UserDetails userDetails);
}