package org.ufcg.si.configurations;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ufcg.si.filters.AuthorizationFilter;
import org.ufcg.si.util.ServerConstants;

/**
 * Class used to configure security related objects.
 *
 */
@Configuration
public class SecurityConfiguration {
	/**
	 * This method configures the filter used to deny users to send
	 * requests without a token.<br>
	 * 
	 * Url Patterns that will be checked:<br>
	 * <ul>
	 * 	<li>/server/userdirectory/*</li>
	 * </ul>
	 * 
	 * @return A bean that carries the configurations of the filter. 
	 */
	@Bean
	public FilterRegistrationBean buildAuthorizationFilter() {
		FilterRegistrationBean userFilterRegistration = new FilterRegistrationBean();
		userFilterRegistration.setFilter(new AuthorizationFilter());
		
		userFilterRegistration.addUrlPatterns(ServerConstants.USERSDIRECTORY_PATTERN);
		
		return userFilterRegistration;
	}
	
}
