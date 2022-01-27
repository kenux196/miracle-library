package org.kenux.miraclelibrary.testutils;

import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static String readJson(ResourceLoader resourceLoader, final String path) throws IOException {
        final InputStream inputStream = resourceLoader.getResource(path).getInputStream();
        return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    }
}