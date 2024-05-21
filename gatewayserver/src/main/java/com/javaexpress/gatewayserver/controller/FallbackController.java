package com.javaexpress.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

	// Mono<String>
	@GetMapping("/contactSupport")
	public Mono<String> contactSupport() {
		return Mono.just("An error occured. Please try after sometime or contact support team");
	}
}
