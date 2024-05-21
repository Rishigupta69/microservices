package com.javaexpress.cards.service.impl;

import com.javaexpress.cards.dto.CardsDto;
import com.javaexpress.cards.entity.Cards;
import com.javaexpress.cards.exception.CardAlreadyExistsException;
import com.javaexpress.cards.repository.CardsRepository;
import com.javaexpress.cards.service.ICardService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CardsServiceImpl implements ICardService {

    @Autowired
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already exists with given Mobile number: "+mobileNumber);
        }
        createNewCard(mobileNumber);
    }

    private void createNewCard(String mobileNumber) {
        Cards cards = new Cards();
        long randomAccountNumber = 10000000L + new Random().nextInt(900000000);
        cards.setCardNumber(String.valueOf(randomAccountNumber));
        cards.setMobileNumber(mobileNumber);
        cards.setCardType("CREDIT_CARD");
        cards.setTotalLimit(100000);
        cards.setAmountUsed(0);
        cards.setAvailableAmount(100000);
        cardsRepository.save(cards);
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards dbCardInfo = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new CardAlreadyExistsException("Card already exists with given Mobile number: "+mobileNumber));
        CardsDto cardsDto = new CardsDto();
        BeanUtils.copyProperties(dbCardInfo,cardsDto);
        return cardsDto;
    }
    // @Mapper(source="",destination="") -mapstructs

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards dbCardInfo = cardsRepository.findByCardNumber(cardsDto.getCardNumber())
                .orElseThrow(()->new CardAlreadyExistsException("Card Number Not exits in Db: "+cardsDto.getCardNumber()));
        BeanUtils.copyProperties(cardsDto,dbCardInfo);
        cardsRepository.save(dbCardInfo);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new CardAlreadyExistsException("MobileNumber is not associated with any card "+mobileNumber));
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
