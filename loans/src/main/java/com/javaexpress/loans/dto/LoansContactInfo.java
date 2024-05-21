package com.javaexpress.loans.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import lombok.Getter;
import lombok.Setter;

@RefreshScope
@ConfigurationProperties(prefix = "loans")
@Setter
@Getter
public class LoansContactInfo {

	private String message;
	private Map<String,String> contactDetails;
	private List<String> onCallSupport;
	
}
