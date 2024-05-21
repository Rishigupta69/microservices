package com.javaexpress.accounts.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class CustomerDto {

	private String name;
	private String email;
	private String mobileNumber;
	private AccountsDto accountsDto;
}
