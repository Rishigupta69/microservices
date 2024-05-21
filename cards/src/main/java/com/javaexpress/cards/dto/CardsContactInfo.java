package com.javaexpress.cards.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import lombok.Getter;
import lombok.Setter;

@RefreshScope
@ConfigurationProperties(prefix = "cards")
@Setter
@Getter
public class CardsContactInfo {

	private String message;
	private Map<String,String> contactDetails;
	private List<String> onCallSupport;
	
}
