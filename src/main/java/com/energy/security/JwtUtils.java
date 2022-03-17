package com.energy.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.energy.service.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expirationms}")
	private Long jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Date now = new Date();
		Date exp = new Date((now).getTime() + jwtExpirationMs);
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(now)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
		return true;
		} catch (Exception e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		}
		return false;
	}

}
