package org.ufcg.si;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.ufcg.si.filters.UserFilter;
import org.ufcg.si.util.ServerConstants;

@SpringBootApplication
public class GreenBoxApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GreenBoxApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean userFilter() {
		FilterRegistrationBean userFilterRegistration = new FilterRegistrationBean();
		userFilterRegistration.setFilter(new UserFilter());
		userFilterRegistration.addUrlPatterns("/userprofile/*");
		return userFilterRegistration;
	}
}
