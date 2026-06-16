package com.ai.automation.framework.api;

import com.ai.automation.framework.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RestAssuredManager {

    private static RequestSpecification requestSpecification;

    private RestAssuredManager() {
    }

    public static RequestSpecification getRequestSpecification() {
        if (requestSpecification == null) {
            requestSpecification = new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.getInstance().getApiBaseUri())
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .build();
        }
        return requestSpecification;
    }
}
