package com.financial.aggregator.financialAggregator.dto;

import java.math.BigDecimal;
import java.util.List;

public class FinancialSummaryResponse {
    private BigDecimal totalBalance;
    private List<AccountDto> accounts;
    private List<LoanDto> loans;
    private List<InvestmentDto> investments;
    private List<CreditCardDto> creditCards;
    private List<SavingDto> savings;

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }

    public List<LoanDto> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanDto> loans) {
        this.loans = loans;
    }

    public List<InvestmentDto> getInvestments() {
        return investments;
    }

    public void setInvestments(List<InvestmentDto> investments) {
        this.investments = investments;
    }

    public List<CreditCardDto> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCardDto> creditCards) {
        this.creditCards = creditCards;
    }

    public List<SavingDto> getSavings() {
        return savings;
    }

    public void setSavings(List<SavingDto> savings) {
        this.savings = savings;
    }
}
