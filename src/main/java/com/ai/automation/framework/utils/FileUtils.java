package com.ai.automation.framework.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public final class FileUtils {

    private FileUtils() {
    }

    public static String getResourcePath(String resourceName) {
        URL resource = FileUtils.class.getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new RuntimeException("Resource not found: " + resourceName);
        }
        try {
            return new File(resource.toURI()).getAbsolutePath();
        } catch (URISyntaxException ex) {
            throw new RuntimeException("Unable to resolve resource path: " + resourceName, ex);
        }
    }

    public static void createDirectoryIfMissing(String path) {
        File directory = new File(path);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Unable to create directory: " + path);
        }
    }
}
