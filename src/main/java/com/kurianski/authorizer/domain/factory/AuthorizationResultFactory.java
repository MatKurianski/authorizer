package com.kurianski.authorizer.domain.factory;

import com.kurianski.authorizer.domain.AccountBalance;
import com.kurianski.authorizer.domain.AuthorizationRequest;
import com.kurianski.authorizer.domain.AuthorizationResult;
import com.kurianski.authorizer.domain.OperationResult;

public class AuthorizationResultFactory {
    private AuthorizationResultFactory() { }

    public static AuthorizationResult build(AuthorizationRequest authorizationRequest, OperationResult operationResult) {
        return new AuthorizationResult(
                authorizationRequest.getTransactionId(),
                authorizationRequest.getAmount(),
                new AccountBalance(authorizationRequest.getAccountId(), null),
                operationResult
        );
    }

    public static AuthorizationResult build(AuthorizationRequest authorizationRequest, AccountBalance accountBalance, OperationResult operationResult) {
        return new AuthorizationResult(
                authorizationRequest.getTransactionId(),
                authorizationRequest.getAmount(),
                accountBalance,
                operationResult
        );
    }
}
