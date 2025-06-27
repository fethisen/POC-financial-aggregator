package com.financial.aggregator.financialAggregator.dto;


import java.math.BigDecimal;

public record LoanDto(String type, BigDecimal remainingDebt) {}

