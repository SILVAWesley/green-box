package org.ufcg.si.util.tokens;

import org.springframework.stereotype.Component;
import org.ufcg.si.util.ServerConstants;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * A token builder that uses JWT and the following constraints:<br>
 * <ul>
 * 	<li>Expiration Time: 2 Minutes</li>
 * 	<li>Algorithm: HS512</li>
 * 	<li>Key: ServerConstants.TOKEN_KEY</li>
 * </ul>
 */
@Component
public class HS512_2Minutes_Token extends AbstractTokenBuilder {
	public static long MINUTES2_TO_MILLIS = 2 * 60 * 1000;
	
	public HS512_2Minutes_Token() {
		super(MINUTES2_TO_MILLIS, SignatureAlgorithm.HS512, ServerConstants.TOKEN_KEY);
	}
	
}
