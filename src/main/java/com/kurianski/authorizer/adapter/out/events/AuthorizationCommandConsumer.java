package com.kurianski.authorizer.adapter.out.events;

import com.kurianski.authorizer.adapter.in.AuthorizationInputPort;
import com.kurianski.authorizer.adapter.out.mapper.AuthorizationCommandToAuthorizationRequestMapper;
import com.kurianski.authorizer.adapter.out.mapper.AuthorizationResultToAuthorizationProcessedMapper;
import com.kurianski.command.AuthorizationCommand;
import com.kurianski.response.AuthorizationProcessed;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorizationCommandConsumer {
    private static final Logger LOGGER = Logger.getLogger("CommandConsumer");

    private final AuthorizationInputPort authorizationInputPort;
    private final AuthorizationCommandToAuthorizationRequestMapper commandmapper;
    private final AuthorizationResultToAuthorizationProcessedMapper responseMapper;

    private final Emitter<AuthorizationProcessed> emitter;

    public AuthorizationCommandConsumer(
            AuthorizationInputPort authorizationInputPort,
            AuthorizationCommandToAuthorizationRequestMapper commandmapper,
            AuthorizationResultToAuthorizationProcessedMapper responseMapper, @Channel("authorization-processed")
                    Emitter<AuthorizationProcessed> emitter
    ) {
        this.authorizationInputPort = authorizationInputPort;
        this.commandmapper = commandmapper;
        this.responseMapper = responseMapper;
        this.emitter = emitter;
    }

    @Incoming("command-authorization-from-kafka")
    public void receive(AuthorizationCommand authorizationCommand) {
        LOGGER.infof("Event received: %s", authorizationCommand.toString());

        Uni.createFrom().item(authorizationCommand)
                .map(commandmapper::map)
                .chain(authorizationInputPort::processTransaction)
                .map(responseMapper::map)
                .subscribe().with(
                        authorizationResponseEvent -> {
                            emitter.send(authorizationResponseEvent);
                            LOGGER.infof("Response sent %s", authorizationResponseEvent.toString());
                        },
                        (exception) -> LOGGER.errorf("An error occurred: %s", exception)
                );
    }
}
