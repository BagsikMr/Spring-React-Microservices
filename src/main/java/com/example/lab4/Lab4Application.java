package com.example.lab4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab4Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab4Application.class, args);
	}


	@Bean
	public RouteLocator routeLocator(
			RouteLocatorBuilder builder,
			@Value("http://localhost:8081") String drinkUrl,
			@Value("http://localhost:8082") String brandUrl,
			@Value("localhost:8084") String host
	){
		return builder
				.routes()
				.route("brands", route -> route
						.host(host)
						.and()
						.path(
								"/api/brands",
								"/api/brands/**",
								"/api/brands/name/{name}"
						)
						.uri(brandUrl)
				)
				.route("drinks", route -> route
						.host(host)
						.and()
						.path(
								"/api/drinks",
								"/api/drinks/**",
								"/api/drinks/name/{name}"
						)
						.uri(drinkUrl)
				)
				.build();
	}
}
