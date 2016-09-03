package org.ufcg.si.util.tokens;

import java.util.Date;

import org.ufcg.si.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * A default implementation for TokenBuilder. Uses a library
 * called JSON Web Token (JWT) to build a token.
 */
public abstract class AbstractTokenBuilder implements TokenBuilder {
	private long expirationTimeInMillis;
	private SignatureAlgorithm algorithm;
	private String tokenKey;
	
	/**
	 * Create a new AbstractTokenBuilder.
	 * 
	 * @param expirationTimeInMillis The amount of time that the token is valid.
	 * @param algorithm An algorithm to encrypt the token.
	 * @param tokenKey A key used to encrypt/decrypt the token.
	 */
	public AbstractTokenBuilder(long expirationTimeInMillis, SignatureAlgorithm algorithm, String tokenKey) {
		this.expirationTimeInMillis = expirationTimeInMillis;
		this.algorithm = algorithm;
		this.tokenKey = tokenKey;
	}
	
	@Override
	public String build(User user) {
		Date expiration = new Date(System.currentTimeMillis() + expirationTimeInMillis);
		
		String token = Jwts.builder().
			   	   	   setSubject(user.getUsername()).
			   	   	   signWith(algorithm, tokenKey).
			   	   	   setExpiration(expiration).
			   	   	   compact();
		
		return token;
	}

}
