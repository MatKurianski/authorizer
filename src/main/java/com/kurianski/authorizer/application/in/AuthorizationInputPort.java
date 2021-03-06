package com.kurianski.authorizer.application.in;

import com.kurianski.authorizer.domain.AuthorizationRequest;
import com.kurianski.authorizer.domain.AuthorizationResult;
import io.smallrye.mutiny.Uni;

public interface AuthorizationInputPort {
    Uni<AuthorizationResult> processTransaction(AuthorizationRequest authorizationRequest);
}
