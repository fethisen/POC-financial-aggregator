package com.loan.loan.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    /**
     * Kredi ve borç bilgileri
     * @param userId
     * @return
     * @throws InterruptedException
     */

    @GetMapping("/{userId}")
    public List<LoanDto> getLoans(@PathVariable String userId) throws InterruptedException{
        TimeUnit.SECONDS.sleep(3);
        return List.of(
                new LoanDto("İhtiyaç Kredisi", new BigDecimal("25000.00")),
                new LoanDto("Konut Kredisi", new BigDecimal("100000.00"))
        );
    }

    record LoanDto(String type, BigDecimal remainingDebt){}
}
