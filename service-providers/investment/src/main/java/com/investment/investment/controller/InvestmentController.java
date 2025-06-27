package com.investment.investment.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    /**
     * Fon, hisse, döviz vb yatırımlar
      * @param userId
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/{userId}")
    public List<InvestmentDto> getInvestments(@PathVariable String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);

        return List.of(
                new InvestmentDto("Fon", new BigDecimal("8000.50")),
                new InvestmentDto("Hisse", new BigDecimal("12000.00"))
        );
    }
}

record InvestmentDto(String type, BigDecimal currentValue) {}