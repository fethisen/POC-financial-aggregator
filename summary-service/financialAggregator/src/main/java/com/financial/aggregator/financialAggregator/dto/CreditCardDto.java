package com.financial.aggregator.financialAggregator.dto;

import java.math.BigDecimal;

public record CreditCardDto(String cardNo, BigDecimal currentDebt) {}
