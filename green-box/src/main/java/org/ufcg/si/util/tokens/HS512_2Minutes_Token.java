package org.ufcg.si.util.tokens;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.ufcg.si.models.User;
import org.ufcg.si.util.ServerConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class HS512_2Minutes_Token implements TokenBuilder{
	private long expirationInMillis;
	private SignatureAlgorithm algorithm;
	private String tokenKey;
	
	public HS512_2Minutes_Token() {
		this.expirationInMillis = 120 * 1000;
		this.algorithm = SignatureAlgorithm.HS512;
		this.tokenKey = ServerConstants.TOKEN_KEY;
	}
	
	public String build(User user) {
		Date expiration = new Date(System.currentTimeMillis() + expirationInMillis);
		
		String token = Jwts.builder().
			   	   	   setSubject(user.getUsername()).
			   	   	   signWith(algorithm, tokenKey).
			   	   	   setExpiration(expiration).
			   	   	   compact();
		
		return token;
	}

}
