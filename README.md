# Currency Exchange Rafał Piąstka 12/08/2024
Hi! Build me like this:

```sh
./gradlew build
```

```sh
docker-comopse up --build
```

## You can test my endpoints like below.
Create account:
```sh
curl -X POST "http://localhost:8080/api/accounts" \
-d "firstName=Rafal" \
-d "lastName=Piastka" \
-d "balancePln=1000.00"
```

One can expect following response:
`{"id":1,"firstName":"Rafal","lastName":"Piastka","balancePln":1000.00,"balanceUsd":0}`

Get account:
```sh
curl -X GET "http://localhost:8080/api/accounts/1" 
```

Exchange PLN -> USD:
```sh
curl -X POST "http://localhost:8080/api/accounts/1/exchange/pln-to-usd" \
-d "amount=100.00"
```

Exchange USD -> PLN:
```sh
curl -X POST "http://localhost:8080/api/accounts/1/exchange/usd-to-pln" \
-d "amount=24.79"
```
