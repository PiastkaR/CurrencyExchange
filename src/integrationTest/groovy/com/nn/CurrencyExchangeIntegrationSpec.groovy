package com.nn

import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.profiles.active=integrationTest")
@AutoConfigureWireMock(port = 0)
@AutoConfigureObservability
abstract class CurrencyExchangeIntegrationSpec extends Specification {
}
