package com.saving.saving.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saving")
public class SavingController {

    /**
     * Vadeli hesaplar, altın, döviz vb.
     * @param userId
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/{userId}")
    public List<SavingDto> getSavings(@PathVariable String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);

        return List.of(
                new SavingDto("Vadeli Mevduat", new BigDecimal("10000.00")),
                new SavingDto("Altın Hesabı", new BigDecimal("3500.00"))
        );
    }
}

record SavingDto(String type, BigDecimal value) {}
