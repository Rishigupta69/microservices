package com.javaexpress.cards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.javaexpress.cards.dto.CardsContactInfo;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(CardsContactInfo.class)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
