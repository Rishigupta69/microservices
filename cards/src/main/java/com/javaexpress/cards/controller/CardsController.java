package com.javaexpress.cards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.cards.dto.CardsContactInfo;
import com.javaexpress.cards.dto.CardsDto;
import com.javaexpress.cards.service.ICardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api")
@Slf4j
public class CardsController {

    @Autowired
    private ICardService iCardService;
    
    @Value("${build.version}")
	private String buildVersion;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CardsContactInfo cardsContactInfo;

    @PostMapping("/create")
    public String createCard(@RequestParam String mobileNumber){
        iCardService.createCard(mobileNumber);
        return "Card Created Successfully";
    }

    @GetMapping("/fetch")
    public CardsDto fetchCardDetails(@RequestParam String mobileNumber){
    	log.info("fetchCardDetails");
        return  iCardService.fetchCard(mobileNumber);
    }

    @PutMapping("/update")
    public Boolean updateCardDetails(@RequestBody CardsDto cardsDto){
        return iCardService.updateCard(cardsDto);
    }

    @DeleteMapping("/delete")
    public Boolean deleteCardDetails(@RequestParam String mobileNumber) {
        return iCardService.deleteCard(mobileNumber);
    }
    
    @GetMapping("build-info")
	public String getBuildVersion() {
		return buildVersion;
	}
	
	@GetMapping("java-version")
	public String getJavaVersion() {
		return environment.getProperty("JAVA_HOME");
	}
	
	@GetMapping("contact-info")
	public CardsContactInfo getContactInfo() {
		return cardsContactInfo;
	}
}
