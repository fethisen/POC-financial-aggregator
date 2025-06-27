package com.creditCard.credit_card.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
public class CreditCardController {

    /**
     * Kredi kartı borcu ve limiti
     * @param userId
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/{userId}")
    public List<CreditCardDto> getCards(@PathVariable String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);

        return List.of(
                new CreditCardDto("**** **** 1111", new BigDecimal("3200.00")),
                new CreditCardDto("**** **** 2222", new BigDecimal("1500.00"))
        );
    }
}

record CreditCardDto(String cardNo, BigDecimal currentDebt) {}
