package org.ufcg.si.util.tokens;

import org.springframework.stereotype.Component;
import org.ufcg.si.util.ServerConstants;

import io.jsonwebtoken.SignatureAlgorithm;


/**
 * A token builder that uses JWT and the following constraints:<br>
 * <ul>
 * 	<li>Expiration Time: 24 Hours</li>
 * 	<li>Algorithm: HS512</li>
 * 	<li>Key: ServerConstants.TOKEN_KEY</li>
 * </ul>
 */
@Component
public class HS512_24Hours_Token extends AbstractTokenBuilder {
	public static long HOURS24_TO_MILLIS = 24 * 60 * 60 * 1000; 
	
	public HS512_24Hours_Token() {
		super(HOURS24_TO_MILLIS, SignatureAlgorithm.HS512, ServerConstants.TOKEN_KEY);
	}
	
}
