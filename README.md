# Authorizer

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## About

This is a personal project to study more about Quarkus, Kafka and DynamoDB. 


Authorizer is a microservice that processes debit and credit commands in users' accounts. 

Customer balance is on a DynamoDB table and every change to it is made using Conditions Expressions to ensure highly consistent writes and avoid problems with concurrent access to the same item.

See more here: https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ConditionExpressions.html

## Running the application in dev mode

First, you need to setup the Kafka broker and DynamoDB locally. Navigate to docker_env folder and run

```shell script
docker-compose up -d
```

After, to create the DynamoDB table you need to run

```shell script
./create_user.sh
```

is recommended to create an account, because for now the application itself doenst have a account registering mechanism. So run the script below too which the first argument is the user id

```shell script
./create_user.sh 3c3666c0-7e7f-46fb-b53d-d2e05a4a9a06
```

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

Now, you can finally test doing a POST request to emit an Authorization Command event.

```shell script
curl --location --request POST 'localhost:8080/test-command' \
--header 'Content-Type: application/json' \
--data-raw '{
"transactionId": "7669e52d-c170-4f9f-b5ad-d8545795e558",
"accountId": "3c3666c0-7e7f-46fb-b53d-d2e05a4a9a06",
"amount": 10
}'
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/authorizer-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Related Guides


## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
