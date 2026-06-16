package com.ai.automation.framework.config;

public enum Environment {
    DEV,
    QA,
    STAGE,
    PROD;

    public static Environment fromString(String value) {
        if (value == null || value.isBlank()) {
            return QA;
        }
        return Environment.valueOf(value.trim().toUpperCase());
    }
}
