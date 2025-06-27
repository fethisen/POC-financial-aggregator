package com.financial.aggregator.financialAggregator.service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AsyncWorkerService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncWorkerService.class);
    private final WebClient webClient;

    private static final int  WAIT_TIME_FOR_WEBCLIENT = 3;
    private static final int  WAIT_TIME_FOR_THREAD = 4;

    public AsyncWorkerService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    /**
     * WebClient.timeout() --> HTTP çağrısının cevaplanmasını bekleme süresi
     * CompletableFuture.orTimeout() --> Eğer WebClient takılmazsa bile thread 4. saniyede kopar
     */
    @Async("taskExecutor")
    public  <T> CompletableFuture<List<T>> fetchListAsync(String url, Class<T> clazz) {
        logger.info("➡️ [Thread: {}] called service: {}", Thread.currentThread().getName(), url);
        return webClient.get().uri(url)
                .retrieve()
                .bodyToFlux(clazz)
                .collectList()
                .timeout(Duration.ofSeconds(WAIT_TIME_FOR_WEBCLIENT)) // WebClient içinde response bekleme süresi
                .onErrorResume(e -> {
                    logger.error("➡️ [Thread: {}] Timeout or error for UR: {}, exception: {}", Thread.currentThread().getName(), url,e.getClass().getSimpleName());
                    return Mono.just(Collections.emptyList());
                })
                .toFuture()
                .orTimeout(WAIT_TIME_FOR_THREAD, TimeUnit.SECONDS) // Future düzeyinde thread zaman sınırı
                .exceptionally(ex -> {
                    logger.error("Thread-level timeout: {}",ex.getClass().getSimpleName());
                    return Collections.emptyList();
                });
    }

}
