package com.reddit.reddit_clone.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
class JwtServiceImpl implements JwtService {
	@Value("${JWT_SECRET_KEY}")
	private String jwtSecretKey;

	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
						.setSigningKey(getSignInKey())
						.build()
						.parseClaimsJws(token)
						.getBody();
	}

	private String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
		return Jwts.builder()
						.setClaims(extractClaims)
						.setSubject(userDetails.getUsername())
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
						.signWith(getSignInKey(), SignatureAlgorithm.HS256)
						.compact();

	}

	private boolean isTokenExpired(String token) {
		return extractExpirationDate(token).before(new Date());
	}

	private Date extractExpirationDate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}














