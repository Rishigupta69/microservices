package com.javaexpress.cards.service;

import com.javaexpress.cards.dto.CardsDto;

public interface ICardService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
