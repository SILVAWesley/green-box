package org.ufcg.si.configurations;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ufcg.si.filters.UserFilter;
import org.ufcg.si.util.ServerConstants;

@Configuration
public class SecurityConfiguration {
	@Bean
	public FilterRegistrationBean userFilter() {
		FilterRegistrationBean userFilterRegistration = new FilterRegistrationBean();
		userFilterRegistration.setFilter(new UserFilter());
		userFilterRegistration.addUrlPatterns(ServerConstants.USERDIRECTORY_PATTERN);
		return userFilterRegistration;
	}
	
}
