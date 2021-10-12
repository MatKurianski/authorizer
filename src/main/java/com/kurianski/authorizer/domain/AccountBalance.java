package com.kurianski.authorizer.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AccountBalance {
    private final UUID accountId;
    private final BigDecimal balance;

    public AccountBalance(UUID accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalance that = (AccountBalance) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance);
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
