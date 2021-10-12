package com.kurianski.authorizer.adapter.out.controller;

import com.kurianski.command.AuthorizationCommand;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test-command")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestCommandProducerResource {

    private static final Logger LOGGER = Logger.getLogger("TestCommandProducerResource");

    @Inject
    @Channel("command-authorization")
    Emitter<AuthorizationCommand> emitter;

    @POST
    public Response sendTestCommand(AuthorizationCommand authorizationCommand) {
        emitter.send(authorizationCommand);
        LOGGER.infof("Test event sent %s", authorizationCommand.toString());
        return Response.accepted().build();
    }
}

