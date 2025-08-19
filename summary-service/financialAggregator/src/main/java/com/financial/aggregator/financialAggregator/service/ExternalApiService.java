package com.financial.aggregator.financialAggregator.service;

import com.financial.aggregator.financialAggregator.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalApiService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiService.class);

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

    private final RestTemplate restTemplate;

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Async("taskExecutor")
    public CompletableFuture<List<AccountDto>> getAccountsAsync(String userId) {
        try {
            String url = accountService + userId;
            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
            ResponseEntity<List<AccountDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountDto>>() {});
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("➡️ [Thread: {}] Accounts service error: {}", Thread.currentThread().getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<LoanDto>> getLoansAsync(String userId) {
        try {
            String url =loanService + userId;
            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
            ResponseEntity<List<LoanDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<LoanDto>>() {});
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("➡️ [Thread: {}] Loans service error: {}", Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<InvestmentDto>> getInvestmentsAsync(String userId) {
        try {
            String url = investmentService + userId;
            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
            ResponseEntity<List<InvestmentDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<InvestmentDto>>() {});
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("➡️ [Thread: {}] Investments service error: {}", Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<CreditCardDto>> getCreditCardsAsync(String userId) {
        try {
            String url = cardService + userId;
            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
            ResponseEntity<List<CreditCardDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CreditCardDto>>() {});
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("➡️ [Thread: {}] Cards service error: {}", Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<List<SavingDto>> getSavingsAsync(String userId) {
        try {
            String url = savingService + userId;
            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
            ResponseEntity<List<SavingDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<SavingDto>>() {});
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("➡️ [Thread: {}] Savings service error: {}", Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }


}
