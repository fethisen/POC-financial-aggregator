package com.financial.aggregator.financialAggregator.controller;

import com.financial.aggregator.financialAggregator.dto.FinancialSummaryResponse;
import com.financial.aggregator.financialAggregator.service.FinancialAggregatorService;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/summary")
public class FinancialAggregatorController {

    private final FinancialAggregatorService service;
    private static final Logger logger = LoggerFactory.getLogger(FinancialAggregatorController.class);


    public FinancialAggregatorController(FinancialAggregatorService service) {
        this.service = service;
    }

    @GetMapping("{userId}")
    public CompletableFuture<ResponseEntity<FinancialSummaryResponse>> getSummary(@PathVariable String userId) {
        logger.info("➡️ [Thread: {}] Received request for userId: {}", Thread.currentThread().getName(), userId);
        return service.getSummary(userId)
                .thenApply(ResponseEntity::ok);
    }

}
