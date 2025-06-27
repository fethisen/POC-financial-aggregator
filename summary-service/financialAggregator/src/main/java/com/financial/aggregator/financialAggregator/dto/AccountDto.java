package com.financial.aggregator.financialAggregator.dto;

import java.math.BigDecimal;

public record AccountDto(String iban, BigDecimal balance) {}
