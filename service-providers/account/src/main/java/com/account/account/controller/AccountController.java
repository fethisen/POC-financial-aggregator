package com.account.account.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    /**
     * Vadesiz ve mevduat hesapları
     * @param userId
     * @return
     * @throws InterruptedException
     */
    @GetMapping("{userId}")
    public List<AccountDto> getAccounts(@PathVariable String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return List.of(
                new AccountDto("TR1234567890", new BigDecimal("12500.00")),
                new AccountDto("TR0987654321", new BigDecimal("3000.00"))
        );
    }

    record AccountDto(String iban, BigDecimal balance) {}
}
