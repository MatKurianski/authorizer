package com.kurianski.authorizer.application.datastore;

import com.kurianski.authorizer.domain.AuthorizationRequest;
import com.kurianski.authorizer.domain.AuthorizationResult;
import io.smallrye.mutiny.Uni;

public interface AccountBalanceOutputPort {
    Uni<AuthorizationResult> processOperation(AuthorizationRequest operationRequest);
}
