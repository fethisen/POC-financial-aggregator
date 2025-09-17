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
public class ExternalApiServiceVirTualThread {

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

    public ExternalApiServiceVirTualThread(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Async("taskVirtualExecutor")
    public CompletableFuture<List<AccountDto>> getAccountsAsync(String userId) {
        try {
            String url = accountService + userId;
            String virtualThreadName = Thread.currentThread().getName();

            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} ] calling service: {}",
                    virtualThreadName, threadType, url);


            // I/O çağrısı - Bu noktada virtual thread PARK olacak
            ResponseEntity<List<AccountDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountDto>>() {});

            logger.info("✅ RESPONSE VirtualThread: {}  received from service: {}",
                    virtualThreadName, url);

            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("❌ ERROR    VirtualThread: {} Savings service error: {}",
                    Thread.currentThread().getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Async("taskVirtualExecutor")
    public CompletableFuture<List<LoanDto>> getLoansAsync(String userId) {
        try {
            String url = loanService + userId;
            String virtualThreadName = Thread.currentThread().getName();

            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} ] calling service: {}",
                    virtualThreadName, threadType, url);


            ResponseEntity<List<LoanDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<LoanDto>>() {});

            logger.info("✅ RESPONSE VirtualThread: {}  received from service: {}",
                    virtualThreadName, url);

            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("❌ ERROR    VirtualThread: {} Savings service error: {}",
                    Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

    @Async("taskVirtualExecutor")
    public CompletableFuture<List<InvestmentDto>> getInvestmentsAsync(String userId) {
        try {
            String url = investmentService + userId;
            String virtualThreadName = Thread.currentThread().getName();

            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} ] calling service: {}",
                    virtualThreadName, threadType, url);

            ResponseEntity<List<InvestmentDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<InvestmentDto>>() {});

            logger.info("✅ RESPONSE VirtualThread: {}  received from service: {}",
                    virtualThreadName, url);

            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("❌ ERROR    VirtualThread: {} Savings service error: {}",
                    Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

    @Async("taskVirtualExecutor")
    public CompletableFuture<List<CreditCardDto>> getCreditCardsAsync(String userId) {
        try {
            String url = cardService + userId;
            String virtualThreadName = Thread.currentThread().getName();
            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";

            logger.info("🚀 REQUEST  [Thread: {} | Type: {} ] calling service: {}",
                    virtualThreadName, threadType, url);

            ResponseEntity<List<CreditCardDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CreditCardDto>>() {});

            logger.info("✅ RESPONSE VirtualThread: {}  received from service: {}",
                    virtualThreadName, url);

            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("❌ ERROR    VirtualThread: {} Savings service error: {}",
                    Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

    @Async("taskVirtualExecutor")
    public CompletableFuture<List<SavingDto>> getSavingsAsync(String userId) {
        try {
            String url = savingService + userId;
            String virtualThreadName = Thread.currentThread().getName();

            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} ] calling service: {}",
                    virtualThreadName, threadType, url);

            ResponseEntity<List<SavingDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<SavingDto>>() {});

            logger.info("✅ RESPONSE VirtualThread: {}  received from service: {}",
                    virtualThreadName, url);

            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            logger.error("❌ ERROR    VirtualThread: {} Savings service error: {}",
                    Thread.currentThread().getName(), e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }
}
