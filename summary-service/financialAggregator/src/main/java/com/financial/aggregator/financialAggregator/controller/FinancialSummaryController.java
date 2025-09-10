package com.financial.aggregator.financialAggregator.controller;

import com.financial.aggregator.financialAggregator.dto.FinancialSummaryResponse;
import com.financial.aggregator.financialAggregator.service.FinancialSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//REST ÖRNEK
@RestController
@RequestMapping("/api")
public class FinancialSummaryController {
    private static final Logger logger = LoggerFactory.getLogger(FinancialSummaryController.class);

    private final FinancialSummaryService financialSummaryService;

    public FinancialSummaryController(FinancialSummaryService financialSummaryService) {
        this.financialSummaryService = financialSummaryService;
    }

    @GetMapping("/financial-summary/{userId}")
    public ResponseEntity<FinancialSummaryResponse> getFinancialSummary(@PathVariable String userId) {
        try {
            Thread currentThread = Thread.currentThread();
            String threadType = currentThread.isVirtual() ? "Virtual" : "Platform";
            logger.info("➡️ [Thread: {} | Type: {} ] Received request for userId: {}",
                currentThread.getName(), threadType, userId);
            FinancialSummaryResponse summary = financialSummaryService.getFinancialSummary(userId);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
