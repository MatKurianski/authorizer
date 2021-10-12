package com.kurianski.authorizer.adapter.out.mapper;

import com.kurianski.authorizer.domain.AuthorizationRequest;
import com.kurianski.command.AuthorizationCommand;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.UUID;

@ApplicationScoped
public class AuthorizationCommandToAuthorizationRequestMapper {

    public AuthorizationRequest map(AuthorizationCommand authorizationCommand) {
        return new AuthorizationRequest(
                UUID.fromString(authorizationCommand.getTransactionId()),
                UUID.fromString(authorizationCommand.getAccountId()),
                BigDecimal.valueOf(authorizationCommand.getAmount())
        );
    }
}
