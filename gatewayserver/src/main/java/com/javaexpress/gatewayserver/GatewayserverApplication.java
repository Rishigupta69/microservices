package com.javaexpress.gatewayserver;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}
	
	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> 
						p.path("/javaexpress/accounts/**")
						.filters(f -> 
							f.rewritePath("/javaexpress/accounts/(?<segment>.*)", "/${segment}")
							.addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString())
							.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
									.setFallbackUri("forward:/contactSupport"))
						)
						.uri("lb://ACCOUNTS"))
				.route(p -> 
					p.path("/javaexpress/cards/**")
					.filters(f -> f.rewritePath("/javaexpress/cards/(?<segment>.*)", "/${segment}"))
					.uri("lb://CARDS"))
				.route(p -> 
					p.path("/javaexpress/loans/**")
					.filters(f -> f.rewritePath("/javaexpress/loans/(?<segment>.*)", "/${segment}"))
					.uri("lb://LOANS"))
				.build();
	}

}
