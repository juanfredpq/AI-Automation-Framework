package com.ai.automation.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

public final class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";
    private static final String ENVIRONMENT_PROPERTY = "environment";
    private static final String DEFAULT_BROWSER = "chrome";
    private static final int DEFAULT_IMPLICIT_WAIT = 10;
    private static final int DEFAULT_EXPLICIT_WAIT = 20;
    private static final ConfigManager INSTANCE = new ConfigManager();

    private final Properties properties = new Properties();

    private ConfigManager() {
        try {
            loadProperties(CONFIG_FILE);
            loadEnvironmentProperties();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration properties", ex);
        }
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    private void loadProperties(String classpathResource) throws IOException {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (input == null) {
                throw new IOException("Configuration file not found: " + classpathResource);
            }
            properties.load(input);
        }
    }

    private void loadEnvironmentProperties() throws IOException {
        String environment = System.getProperty(ENVIRONMENT_PROPERTY, properties.getProperty(ENVIRONMENT_PROPERTY, "qa"));
        String envFile = String.format("config/%s.properties", environment.toLowerCase(Locale.US));
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(envFile)) {
            if (input != null) {
                Properties envProperties = new Properties();
                envProperties.load(input);
                properties.putAll(envProperties);
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getBrowser() {
        return System.getProperty(Settings.BROWSER, properties.getProperty(Settings.BROWSER, DEFAULT_BROWSER));
    }

    public String getBaseUrl() {
        return firstNonNull(properties.getProperty(Settings.BASE_URL), properties.getProperty(Settings.URL));
    }

    public String getApiBaseUri() {
        return properties.getProperty(Settings.API_BASE_URI);
    }

    public String getEnvironmentName() {
        return System.getProperty(Settings.ENVIRONMENT, properties.getProperty(Settings.ENVIRONMENT, "qa"));
    }

    public Environment getEnvironment() {
        return Environment.fromString(getEnvironmentName());
    }

    public int getImplicitWait() {
        return parseIntProperty(Settings.IMPLICIT_WAIT, DEFAULT_IMPLICIT_WAIT);
    }

    public int getExplicitWait() {
        return parseIntProperty(Settings.EXPLICIT_WAIT, DEFAULT_EXPLICIT_WAIT);
    }

    public Timeouts getTimeouts() {
        return new Timeouts(Duration.ofSeconds(getImplicitWait()), Duration.ofSeconds(getExplicitWait()));
    }

    public String getReportPath() {
        return properties.getProperty(Settings.REPORT_PATH, "target/reports");
    }

    public String getReportFile() {
        return properties.getProperty(Settings.REPORT_FILE, "Automation-Execution-Report.html");
    }

    public String getDatabaseUrl() {
        return properties.getProperty(Settings.DATABASE_URL);
    }

    public String getDatabaseUsername() {
        return properties.getProperty(Settings.DATABASE_USERNAME);
    }

    public String getDatabasePassword() {
        return properties.getProperty(Settings.DATABASE_PASSWORD);
    }

    private int parseIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Invalid integer value for property '" + key + "': " + value, ex);
        }
    }

    private static String firstNonNull(String first, String second) {
        return first != null ? first : second;
    }

    public static final class Timeouts {

        private final Duration implicitWait;
        private final Duration explicitWait;

        public Timeouts(Duration implicitWait, Duration explicitWait) {
            this.implicitWait = Objects.requireNonNull(implicitWait, "implicitWait");
            this.explicitWait = Objects.requireNonNull(explicitWait, "explicitWait");
        }

        public Duration getImplicitWait() {
            return implicitWait;
        }

        public Duration getExplicitWait() {
            return explicitWait;
        }
    }
}
