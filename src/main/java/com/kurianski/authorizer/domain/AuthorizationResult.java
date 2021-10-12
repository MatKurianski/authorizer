package com.kurianski.authorizer.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AuthorizationResult {
    private UUID transactionId;
    private BigDecimal amount;
    private AccountBalance accountBalance;
    private OperationResult operationResult;

    public AuthorizationResult(UUID transactionId, BigDecimal amount, AccountBalance accountBalance, OperationResult operationResult) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.accountBalance = accountBalance;
        this.operationResult = operationResult;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationResult that = (AuthorizationResult) o;
        return transactionId.equals(that.transactionId) && amount.equals(that.amount) && accountBalance.equals(that.accountBalance) && operationResult == that.operationResult;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, amount, accountBalance, operationResult);
    }

    @Override
    public String toString() {
        return "AuthorizationResult{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", accountBalance=" + accountBalance +
                ", operationResult=" + operationResult +
                '}';
    }
}
