package com.javaexpress.accounts.service.clients;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaexpress.accounts.dto.CardsDto;

@FeignClient(name="CARDS",fallback = CardsFallback.class) // IP:PORT
@LoadBalancerClient("CARDS")
public interface CardsFeignClient {

	@GetMapping("api/fetch")
    public CardsDto fetchCardDetails(@RequestParam String mobileNumber);
}
