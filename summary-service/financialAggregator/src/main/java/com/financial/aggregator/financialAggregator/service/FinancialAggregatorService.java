package com.financial.aggregator.financialAggregator.service;

import com.financial.aggregator.financialAggregator.dto.AccountDto;
import com.financial.aggregator.financialAggregator.dto.CreditCardDto;
import com.financial.aggregator.financialAggregator.dto.FinancialSummaryResponse;
import com.financial.aggregator.financialAggregator.dto.InvestmentDto;
import com.financial.aggregator.financialAggregator.dto.LoanDto;
import com.financial.aggregator.financialAggregator.dto.SavingDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FinancialAggregatorService {
    private static final Logger logger = LoggerFactory.getLogger(FinancialAggregatorService.class);

    @Value("${services.account}")
    private String accountService;

    @Value("${services.loan}")
    private String loanService;

    @Value("${services.investment}")
    private String investmentService;

    @Value("${services.card}")
    private String cardService;

    @Value("${services.saving}")
    private String savingService;

    private final AsyncWorkerService asyncWorkerService;

    public FinancialAggregatorService(AsyncWorkerService asyncWorkerService) {
        this.asyncWorkerService = asyncWorkerService;
    }

    public CompletableFuture<FinancialSummaryResponse> getSummary(String userId) {
        CompletableFuture<List<AccountDto>> accountsF = asyncWorkerService.fetchListAsync(accountService + userId, AccountDto.class);
        CompletableFuture<List<LoanDto>> loansF = asyncWorkerService.fetchListAsync(loanService + userId, LoanDto.class);
        CompletableFuture<List<InvestmentDto>> investmentsF = asyncWorkerService.fetchListAsync(investmentService + userId, InvestmentDto.class);
        CompletableFuture<List<CreditCardDto>> cardsF = asyncWorkerService.fetchListAsync(cardService + userId, CreditCardDto.class);
        CompletableFuture<List<SavingDto>> savingsF = asyncWorkerService.fetchListAsync(savingService + userId, SavingDto.class);

        return CompletableFuture.allOf(accountsF,loansF,investmentsF,cardsF,savingsF)
                .thenApply(v->{
                    List<AccountDto> accounts = accountsF.join();
                    List<LoanDto> loans = loansF.join();
                    List<InvestmentDto> investments = investmentsF.join();
                    List<CreditCardDto> cards = cardsF.join();
                    List<SavingDto> savings = savingsF.join();

                    BigDecimal total = BigDecimal.ZERO;
                    for (var a : accounts) total = total.add(a.balance());
                    for (var s : savings) total = total.add(s.value());
                    for (var i : investments) total = total.add(i.currentValue());
                    for (var l : loans) total = total.subtract(l.remainingDebt());
                    for (var c : cards) total = total.subtract(c.currentDebt());

                    var res = new FinancialSummaryResponse();
                    res.setAccounts(accounts);
                    res.setLoans(loans);
                    res.setInvestments(investments);
                    res.setCreditCards(cards);
                    res.setSavings(savings);
                    res.setTotalBalance(total);
                    return res;
                });
    }

}
