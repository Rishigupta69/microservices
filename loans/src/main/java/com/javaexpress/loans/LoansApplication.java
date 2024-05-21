package com.javaexpress.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.javaexpress.loans.dto.LoansContactInfo;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(LoansContactInfo.class)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
