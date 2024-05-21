package com.javaexpress.accounts.service.clients;

import org.springframework.stereotype.Component;

import com.javaexpress.accounts.dto.CardsDto;

@Component
public class CardsFallback implements CardsFeignClient{

	@Override
	public CardsDto fetchCardDetails(String mobileNumber) {
		CardsDto cardsDto = new CardsDto();
		cardsDto.setStatus("Please try again after sometime");
		return cardsDto;
	}

	
}
