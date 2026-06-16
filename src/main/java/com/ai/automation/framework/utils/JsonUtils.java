package com.ai.automation.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static JsonNode readJson(String resourcePath) {
        try (InputStream input = JsonUtils.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new RuntimeException("JSON resource not found: " + resourcePath);
            }
            return OBJECT_MAPPER.readTree(input);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to read JSON resource: " + resourcePath, ex);
        }
    }
}
