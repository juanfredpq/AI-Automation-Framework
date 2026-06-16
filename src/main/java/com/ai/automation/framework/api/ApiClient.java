package com.ai.automation.framework.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiClient {

    private final RequestSpecification requestSpec;

    public ApiClient() {
        this.requestSpec = RestAssuredManager.getRequestSpecification();
    }

    public Response get(String endpoint, Map<String, ?> queryParams) {
        return requestSpec
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response post(String endpoint, Object body) {
        return requestSpec
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
}
