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


//    @Async("taskExecutor")
    @Async("taskHibritExecutor")
    public CompletableFuture<List<AccountDto>> getAccountsAsync(String userId) {
        try {
            String url = accountService + userId;
            String virtualThreadName = Thread.currentThread().getName();
            String requestCarrier = getCarrierThreadInfo();
            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
//            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
//            //TODO: Bunları virtual thredde aç
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} | Carrier: {}] calling service: {}",
                virtualThreadName, threadType, requestCarrier, url);



            // I/O çağrısı - Bu noktada virtual thread PARK olacak
            ResponseEntity<List<AccountDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountDto>>() {});
            
            // Response döndükten sonra - Virtual thread UNPARK oldu, hangi carrier ile?
            String responseCarrier = getCarrierThreadInfo();
            logger.info("✅ RESPONSE [VirtualThread: {} -> Carrier: {}] received from service: {} | Carrier Switch: {}",
                virtualThreadName, responseCarrier, url,
                !requestCarrier.equals(responseCarrier) ? "YES" : "NO");
            
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            String errorCarrier = getCarrierThreadInfo();
            logger.error("❌ ERROR    [VirtualThread: {} -> Carrier: {}] Accounts service error: {}", 
                Thread.currentThread().getName(), errorCarrier, e.getMessage());
            throw new RuntimeException(e);
        }
    }

//    @Async("taskExecutor")
    @Async("taskHibritExecutor")
    public CompletableFuture<List<LoanDto>> getLoansAsync(String userId) {
        try {
            String url = loanService + userId;
            String virtualThreadName = Thread.currentThread().getName();
            String requestCarrier = getCarrierThreadInfo();
//            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
//            //TODO: Bunları virtual thredde aç
            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} | Carrier: {}] calling service: {}",
                virtualThreadName, threadType, requestCarrier, url);

            ResponseEntity<List<LoanDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<LoanDto>>() {});
            
            String responseCarrier = getCarrierThreadInfo();
            logger.info("✅ RESPONSE [VirtualThread: {} -> Carrier: {}] received from service: {} | Carrier Switch: {}",
                virtualThreadName, responseCarrier, url,
                !requestCarrier.equals(responseCarrier) ? "YES" : "NO");
            
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            String errorCarrier = getCarrierThreadInfo();
            logger.error("❌ ERROR    [VirtualThread: {} -> Carrier: {}] Loans service error: {}", 
                Thread.currentThread().getName(), errorCarrier, e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

//    @Async("taskExecutor")
    @Async("taskHibritExecutor")
    public CompletableFuture<List<InvestmentDto>> getInvestmentsAsync(String userId) {
        try {
            String url = investmentService + userId;
            String virtualThreadName = Thread.currentThread().getName();
            String requestCarrier = getCarrierThreadInfo();
//            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
//            //TODO: Bunları virtual thredde aç
            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} | Carrier: {}] calling service: {}",
                virtualThreadName, threadType, requestCarrier, url);

            ResponseEntity<List<InvestmentDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<InvestmentDto>>() {});
            
            String responseCarrier = getCarrierThreadInfo();
            logger.info("✅ RESPONSE [VirtualThread: {} -> Carrier: {}] received from service: {} | Carrier Switch: {}",
                virtualThreadName, responseCarrier, url,
                !requestCarrier.equals(responseCarrier) ? "YES" : "NO");
            
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            String errorCarrier = getCarrierThreadInfo();
            logger.error("❌ ERROR    [VirtualThread: {} -> Carrier: {}] Investments service error: {}", 
                Thread.currentThread().getName(), errorCarrier, e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

//    @Async("taskExecutor")
    @Async("taskHibritExecutor")
    public CompletableFuture<List<CreditCardDto>> getCreditCardsAsync(String userId) {
        try {
            String url = cardService + userId;
            String virtualThreadName = Thread.currentThread().getName();
            String requestCarrier = getCarrierThreadInfo();
//            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
//            //TODO: Bunları virtual thredde aç
            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} | Carrier: {}] calling service: {}",
                virtualThreadName, threadType, requestCarrier, url);

            ResponseEntity<List<CreditCardDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CreditCardDto>>() {});
            
            String responseCarrier = getCarrierThreadInfo();
            logger.info("✅ RESPONSE [VirtualThread: {} -> Carrier: {}] received from service: {} | Carrier Switch: {}",
                virtualThreadName, responseCarrier, url,
                !requestCarrier.equals(responseCarrier) ? "YES" : "NO");
            
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            String errorCarrier = getCarrierThreadInfo();
            logger.error("❌ ERROR    [VirtualThread: {} -> Carrier: {}] Cards service error: {}", 
                Thread.currentThread().getName(), errorCarrier, e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }

//    @Async("taskExecutor")
    @Async("taskHibritExecutor")
    public CompletableFuture<List<SavingDto>> getSavingsAsync(String userId) {
        try {
            String url = savingService + userId;
            String virtualThreadName = Thread.currentThread().getName();
            String requestCarrier = getCarrierThreadInfo();
//            logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
//            //TODO: Bunları virtual thredde aç
            String threadType = Thread.currentThread().isVirtual() ? "Virtual" : "Platform";
            logger.info("🚀 REQUEST  [Thread: {} | Type: {} | Carrier: {}] calling service: {}",
                virtualThreadName, threadType, requestCarrier, url);

            ResponseEntity<List<SavingDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<SavingDto>>() {});
            
            String responseCarrier = getCarrierThreadInfo();
            logger.info("✅ RESPONSE [VirtualThread: {} -> Carrier: {}] received from service: {} | Carrier Switch: {}",
                virtualThreadName, responseCarrier, url,
                !requestCarrier.equals(responseCarrier) ? "YES" : "NO");
            
            return CompletableFuture.completedFuture(response.getBody());
        } catch (Exception e) {
            String errorCarrier = getCarrierThreadInfo();
            logger.error("❌ ERROR    [VirtualThread: {} -> Carrier: {}] Savings service error: {}", 
                Thread.currentThread().getName(), errorCarrier, e.getMessage());
            return CompletableFuture.completedFuture(List.of());
        }
    }


    /**
     * Carrier thread bilgisini almak için yardımcı metod
     * Virtual thread'in hangi carrier thread üzerinde çalıştığını gösterir
     */
    private String getCarrierThreadInfo() {
        try {
            Thread currentThread = Thread.currentThread();
            if (currentThread.isVirtual()) {
                // Virtual thread için carrier thread bilgisini al
                // Java 21+ ile daha detaylı bilgi alınabilir
                String threadInfo = currentThread.toString();

                // Thread dump'tan carrier bilgisini parse etmeye çalış
                if (threadInfo.contains("ForkJoinPool")) {
                    int start = threadInfo.indexOf("ForkJoinPool");
                    int end = threadInfo.indexOf("]", start);
                    if (end > start) {
                        return threadInfo.substring(start, end);
                    }
                }
                // Fallback: Thread ID ile carrier pool bilgisi
                return String.format("ForkJoinPool-Carrier-%d",
                        Math.abs(currentThread.hashCode()) % 10);
            } else {
                // Platform thread ise direkt ismini döndür
                return currentThread.getName();
            }
        } catch (Exception e) {
            logger.debug("Carrier thread bilgisi alınamadı: {}", e.getMessage());
            return "Unknown-Carrier";
        }
    }
}
