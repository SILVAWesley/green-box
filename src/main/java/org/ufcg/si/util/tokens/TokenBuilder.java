package org.ufcg.si.util.tokens;

import org.ufcg.si.models.User;

/**
 * Interface that all classes which are able to generate tokens
 * should implement.
 */
public interface TokenBuilder {
	/**
	 * Method that is used to generate a new token. The token will be generated
	 * according to the constraints defined in each concrete implementation of 
	 * this method.
	 * 
	 * @param user The user that the token is being generated to.
	 * @return A token.
	 */
	public String build(User user);
}
