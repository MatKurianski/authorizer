package com.kurianski.authorizer.domain;


import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AuthorizationRequest {
    private final UUID transactionId;
    private final UUID accountId;
    private final BigDecimal amount;

    public AuthorizationRequest(UUID transactionId, UUID accountId, BigDecimal amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationRequest that = (AuthorizationRequest) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(accountId, that.accountId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountId, amount);
    }

    @Override
    public String toString() {
        return "OperationRequest{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                '}';
    }
}
