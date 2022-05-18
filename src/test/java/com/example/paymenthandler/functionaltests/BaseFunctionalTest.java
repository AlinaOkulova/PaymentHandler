package com.example.paymenthandler.functionaltests;

import com.example.paymenthandler.PaymentHandlerApplication;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PaymentHandlerApplication.class)
@AutoConfigureWireMock(port = 8083)
@ActiveProfiles(value = "test")
@Slf4j
public abstract class BaseFunctionalTest {

    @LocalServerPort
    protected int serverPort;

    @BeforeEach
    public void setUpRestAssured() {
        RestAssured.port = serverPort;
    }
}
