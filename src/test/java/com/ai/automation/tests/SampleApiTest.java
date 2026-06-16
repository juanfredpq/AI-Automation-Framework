package com.ai.automation.tests;

import com.ai.automation.framework.api.ApiClient;
import com.ai.automation.framework.reporting.ExtentReportListener;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Collections;

@Listeners(ExtentReportListener.class)
public class SampleApiTest {

    @Test(description = "Verify API service responds successfully")
    public void verifyApiServiceIsAvailable() {
        ApiClient apiClient = new ApiClient();
        Response response = apiClient.get("/status", Collections.emptyMap());

        Assert.assertEquals(response.getStatusCode(), 200, "API should return HTTP 200");
        Assert.assertTrue(response.getBody().asString().length() > 0, "Response body should not be empty");
    }
}
