package com.backend.fundoo.util;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Component
public class JwtGenerator {

private static final String SECRET = "123456789";

	
	public String createJwtToken(long id) {
		String generatedToken = null;
		try {
			generatedToken = JWT.create().withClaim("id", id).sign(Algorithm.HMAC256(SECRET));
		} catch (IllegalArgumentException | JWTCreationException e) {
			e.printStackTrace();
		}
		return generatedToken;
	}

	public Long parseJWT(String jwtToken) {
		Long userId = (long) 0;
		try {
			if (jwtToken != null) {
				userId = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(jwtToken).getClaim("id").asLong();
			}
		} catch (IllegalArgumentException | JWTCreationException e) {
			e.printStackTrace();
		}
		return userId;
		
	}
}

