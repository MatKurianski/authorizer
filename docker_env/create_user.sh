aws dynamodb put-item --table-name balance --item "{\"account_id\": {\"S\": \"$1\"}, \"balance\": {\"N\": \"0\"}}" --endpoint-url=http://localhost:7000
