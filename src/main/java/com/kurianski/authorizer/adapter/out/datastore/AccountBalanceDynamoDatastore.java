package com.kurianski.authorizer.adapter.out.datastore;

import com.kurianski.authorizer.domain.factory.AuthorizationResultFactory;
import com.kurianski.authorizer.application.out.AccountBalanceOutputPort;
import com.kurianski.authorizer.domain.AccountBalance;
import com.kurianski.authorizer.domain.AuthorizationRequest;
import com.kurianski.authorizer.domain.AuthorizationResult;
import com.kurianski.authorizer.domain.OperationResult;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class AccountBalanceDynamoDatastore implements AccountBalanceOutputPort {
    private static final String ACCOUNT_ID_ATTR_NAME = "account_id";
    private static final String BALANCE_TABLE_NAME = "balance";
    private static final String BALANCE_AMOUNT_ATTR_NAME = "balance";

    private static final Logger LOGGER = Logger.getLogger("AccountBalanceDynamoDatastore");

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public AccountBalanceDynamoDatastore(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }

    @Override
    public Uni<AuthorizationResult> processOperation(AuthorizationRequest authorizationRequest) {
        var amount = authorizationRequest.getAmount();
        return Uni.createFrom().completionStage(
                amount.signum() == 1 ? processCredit(authorizationRequest) : processDebit(authorizationRequest)
        ).map(putItemResponse -> AuthorizationResultFactory.build(
                authorizationRequest,
                new AccountBalance(authorizationRequest.getAccountId(), new BigDecimal(
                        putItemResponse.attributes().get(BALANCE_AMOUNT_ATTR_NAME).n()
                )),
                OperationResult.SUCCESS
        )).onFailure().recoverWithItem(exception -> {
            var operationResult = exception instanceof ConditionalCheckFailedException ?
                    OperationResult.INSUFFICIENT_BALANCE : OperationResult.SYSTEM_UNAVAILABLE;

            return AuthorizationResultFactory.build(
                    authorizationRequest,
                    operationResult);
        });
    }

    private CompletableFuture<UpdateItemResponse> processCredit(AuthorizationRequest authorizationRequest) {
        return dynamoDbAsyncClient.updateItem(UpdateItemRequest.builder()
                .tableName(BALANCE_TABLE_NAME)
                .key(Map.of(ACCOUNT_ID_ATTR_NAME, AttributeValue.builder().s(authorizationRequest.getAccountId().toString()).build()))
                .updateExpression(String.format("SET %1$s = %1$s + :amount", BALANCE_AMOUNT_ATTR_NAME))
                .expressionAttributeValues(Map.of(
                        ":amount", AttributeValue.builder()
                                .n(authorizationRequest.getAmount().abs().toString())
                                .build()
                ))
                .returnValues(ReturnValue.UPDATED_NEW)
                .build());
    }

    private CompletableFuture<UpdateItemResponse> processDebit(AuthorizationRequest authorizationRequest) {
        return dynamoDbAsyncClient.updateItem(UpdateItemRequest.builder()
                .tableName(BALANCE_TABLE_NAME)
                .key(Map.of(ACCOUNT_ID_ATTR_NAME, AttributeValue.builder().s(authorizationRequest.getAccountId().toString()).build()))
                .updateExpression(String.format("SET %1$s = %1$s - :amount", BALANCE_AMOUNT_ATTR_NAME))
                .conditionExpression(String.format("%1$s >= :amount", BALANCE_AMOUNT_ATTR_NAME))
                .expressionAttributeValues(Map.of(
                        ":amount", AttributeValue.builder()
                                .n(authorizationRequest.getAmount().abs().toString())
                                .build()
                ))
                .returnValues(ReturnValue.UPDATED_NEW)
                .build());
    }
}
