# Currency Exchange Rafał Piąstka 12/08/2024
Hi! Build me like this:

```sh
./gradlew build
```
Please make sure that your `docker running` before  compose.
```sh
docker-comopse up --build
```

## You can test my endpoints like below.
Create account:
```sh
curl -X POST "http://localhost:8080/api/accounts" \
    -H "Content-Type: application/json" \
    -d '{
        "firstName": "Rafal",
        "lastName": "Piastka",
        "balancePln": 1000.00
    }'
```

One can expect following response:
`{
"id": 1,
"firstName": "Rafal",
"lastName": "Piastka",
"balancePln": 1000.00,
"balanceUsd": 0
}`

Get account:
```sh
curl -X GET "http://localhost:8080/api/accounts/1"
```

Exchange PLN -> USD:
```sh
curl -X POST "http://localhost:8080/api/accounts/1/exchange" \
    -H "Content-Type: application/json" \
    -d '{
        "amount": 100.00,
        "fromCurrency": "PLN",
        "toCurrency": "USD"
    }'
```

Exchange USD -> PLN:
```sh
curl -X POST "http://localhost:8080/api/accounts/1/exchange" \
    -H "Content-Type: application/json" \
    -d '{
        "amount": 24.79,
        "fromCurrency": "USD",
        "toCurrency": "PLN"
    }'
```
## IMPORTANT
Please note that docker container will keep the data for app reset, however destroying the container will result in data loss.
In order to have full control over data retention a cron job on EC2 instance with dump is highly recommended.

Expected correct response from NBP API:
`
 <200 OK OK, {"table":"A","currency":"dolar amerykański","code":"USD","rates":[{"no":"237/
A/NBP/2024","effectiveDate":"2024-12-06","mid":4.0341}]},
 [Date:"Sun, 08 Dec 2024 11:31:10 GMT", Cache-Control:"no-cache", Pragma:"no-cache",
 Content-Length:"134", Content-Type:"application/json; charset=utf-8",
 Expires:"-1", ETag:""E+/A5Kh6Hop01JvV7Cj3LwBZ3erYAzneitfNrw7mxmY="",
 Set-Cookie:"ee3la5eizeiY4Eix=jei1Xah3; path=/"]>
`