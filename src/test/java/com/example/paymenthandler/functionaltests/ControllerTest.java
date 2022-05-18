package com.example.paymenthandler.functionaltests;

import com.example.paymenthandler.util.RestAssuredUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;

@Testcontainers
public class ControllerTest extends BaseFunctionalTest {

    @Test
    void handlePayment() throws IOException {

        try(InputStream newPaymentsIS = this.getClass().getResourceAsStream("new_payments.json")) {
            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(newPaymentsIS)
                    .post("/api/payment-handler")
                    .then()
                    .log().body()
                    .statusCode(200);
        }
    }
}
