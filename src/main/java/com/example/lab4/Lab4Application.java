package com.example.lab4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Lab4Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab4Application.class, args);
	}


	@Bean
	public RouteLocator routeLocator(
			RouteLocatorBuilder builder,
			@Value("${lab.drink.url}") String drinkUrl,
			@Value("${lab.brand.url}") String brandUrl,
			@Value("${lab.gateway.host}") String host
	){
		return builder
				.routes()
				.route("brands", route -> route
						.host(host)
						.and()
						.path(
								"/api/brands",
								"/api/brands/**",
								"/api/brands/{uuid}"
						)
						.uri(brandUrl)
				)
				.route("drinks", route -> route
						.host(host)
						.and()
						.path(
								"/api/drinks",
								"/api/drinks/**",
								"/api/drinks/{uuid}",
								"/api/brands/{uuid}/drinks"
						)
						.uri(drinkUrl)
				)
				.build();
	}

	@Bean
	public CorsWebFilter corsWebFilter() {

		final CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Collections.singletonList("*"));
		corsConfig.setMaxAge(3600L);
		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT","PATCH"));
		corsConfig.addAllowedHeader("*");

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsWebFilter(source);
	}
}
