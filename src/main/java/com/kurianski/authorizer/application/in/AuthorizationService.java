package com.kurianski.authorizer.application.in;

import com.kurianski.authorizer.application.out.AccountBalanceOutputPort;
import com.kurianski.authorizer.domain.AuthorizationRequest;
import com.kurianski.authorizer.domain.AuthorizationResult;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorizationService implements AuthorizationInputPort {
    private final AccountBalanceOutputPort accountBalanceOutputPort;

    public AuthorizationService(AccountBalanceOutputPort accountBalanceOutputPort) {
        this.accountBalanceOutputPort = accountBalanceOutputPort;
    }

    @Override
    public Uni<AuthorizationResult> processTransaction(AuthorizationRequest authorizationRequest) {
        return accountBalanceOutputPort.processOperation(authorizationRequest);
    }
}
