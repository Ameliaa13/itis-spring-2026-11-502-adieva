package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class MessageSource {

    private static MessageSource instance;
    private final Map<Locale, Map<String, String>> localization = new HashMap<>();

    private MessageSource() throws IOException {
        loadMessages(Locale.ENGLISH, "messages_en.properties");
        loadMessages(Locale.forLanguageTag("ru"), "messages_ru.properties");

        if (localization.isEmpty()) {
            throw new IllegalArgumentException("Messages files not found");
        }
    }

    public static MessageSource getInstance() throws IOException {
        if (instance == null) {
            instance = new MessageSource();
        }

        return instance;
    }

    public String getMessage(String key, Locale locale, Object... args) {
        Map<String, String> messages = localization.get(locale);
        if (messages == null) {
            throw new IllegalArgumentException("Unknown locale: " + locale);
        }

        String message = messages.get(key);
        if (message == null) {
            throw new IllegalArgumentException("Key not found: " + key);
        }

        return new MessageFormat(message, locale).format(args);
    }

    private void loadMessages(Locale locale, String resourceName) throws IOException {
        Map<String, String> messages = new HashMap<>();

        try (InputStream inputStream = MessageSource.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Messages file not found: " + resourceName);
            }

            Properties properties = new Properties();
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            properties.forEach((key, value) -> messages.put(key.toString(), value.toString()));
        }

        localization.put(locale, messages);
    }
}
