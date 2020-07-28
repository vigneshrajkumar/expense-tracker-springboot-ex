package com.firstpriniples.expensetrackerapi;

import com.firstpriniples.expensetrackerapi.filters.AuthFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}

	// TO be redone
	// @Bean
	// public FilterRegistrationBean<CorsFilter> corsFilter(){
	// 	FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
	// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	// 	CorsConfiguration config = new CorsConfiguration();
	// 	config.addAllowedOrigin("*");
	// 	config.addAllowedHeader("*");
	// 	source.registerCorsConfiguration("/**", config);
	// 	registrationBean.setFilter(new CorsFilter(source));
	// 	registrationBean.setOrder(0);
	// 	return registrationBean;

	// }

	// Registring filters to the app, protecting categories API
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
	}

}
