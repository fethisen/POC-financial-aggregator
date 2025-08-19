package com.financial.aggregator.financialAggregator.service;

import com.financial.aggregator.financialAggregator.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FinancialSummaryService {
    private static final Logger logger = LoggerFactory.getLogger(FinancialSummaryService.class);

    private final ExternalApiService externalApiService;

    public FinancialSummaryService(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }


    public FinancialSummaryResponse  getFinancialSummary(String userId) throws ExecutionException, InterruptedException {
        CompletableFuture<List<AccountDto>> accountsFuture = externalApiService.getAccountsAsync(userId);
        CompletableFuture<List<LoanDto>> loansFuture = externalApiService.getLoansAsync(userId);
        CompletableFuture<List<InvestmentDto>> investmentsFuture = externalApiService.getInvestmentsAsync(userId);
        CompletableFuture<List<CreditCardDto>> creditCardsFuture = externalApiService.getCreditCardsAsync(userId);
        CompletableFuture<List<SavingDto>> savingsFuture = externalApiService.getSavingsAsync(userId);

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                accountsFuture, loansFuture, investmentsFuture, creditCardsFuture, savingsFuture);

        allFutures.join();

        List<AccountDto> accounts = accountsFuture.get();
        List<LoanDto> loans = loansFuture.get();
        List<InvestmentDto> investments = investmentsFuture.get();
        List<CreditCardDto> creditCards = creditCardsFuture.get();
        List<SavingDto> savings = savingsFuture.get();

        BigDecimal total = BigDecimal.ZERO;
        for (var a : accounts) total = total.add(a.balance());
        for (var s : savings) total = total.add(s.value());
        for (var i : investments) total = total.add(i.currentValue());
        for (var l : loans) total = total.subtract(l.remainingDebt());
        for (var c : creditCards) total = total.subtract(c.currentDebt());

        var res = new FinancialSummaryResponse();
        res.setAccounts(accounts);
        res.setLoans(loans);
        res.setInvestments(investments);
        res.setCreditCards(creditCards);
        res.setSavings(savings);
        res.setTotalBalance(total);
        return res;
    }


}
