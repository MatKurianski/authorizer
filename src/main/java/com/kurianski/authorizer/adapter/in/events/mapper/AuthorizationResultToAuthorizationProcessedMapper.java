package com.kurianski.authorizer.adapter.in.events.mapper;

import com.kurianski.authorizer.domain.AccountBalance;
import com.kurianski.authorizer.domain.AuthorizationResult;
import com.kurianski.response.AuthorizationProcessed;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class AuthorizationResultToAuthorizationProcessedMapper {

    public AuthorizationProcessed map(AuthorizationResult authorizationResult) {
        return AuthorizationProcessed.newBuilder()
                .setTransactionId(authorizationResult.getTransactionId().toString())
                .setAccountId(authorizationResult.getAccountBalance().getAccountId().toString())
                .setAmount(authorizationResult.getAmount().doubleValue())
                .setResult(authorizationResult.getOperationResult().toString())
                .setAccountBalance(Optional.of(authorizationResult).map(AuthorizationResult::getAccountBalance).map(AccountBalance::getBalance).map(BigDecimal::doubleValue).orElse(null))
                .build();
    }
}
