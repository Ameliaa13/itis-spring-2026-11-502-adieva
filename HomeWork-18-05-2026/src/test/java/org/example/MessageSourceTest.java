package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageSourceTest {

    @Test
    void getInstanceReturnsSameObject() throws IOException {
        MessageSource first = MessageSource.getInstance();
        MessageSource second = MessageSource.getInstance();

        assertSame(first, second);
    }

    @Test
    void getMessageReturnsEnglishMessage() throws IOException {
        MessageSource messageSource = MessageSource.getInstance();

        String message = messageSource.getMessage("WELCOME", Locale.ENGLISH);

        assertEquals("HELLO", message);
    }

    @Test
    void getMessageReturnsRussianMessage() throws IOException {
        MessageSource messageSource = MessageSource.getInstance();

        String message = messageSource.getMessage("WELCOME", Locale.forLanguageTag("ru"));

        assertEquals("привет", message);
    }

    @Test
    void getMessageSubstitutesArgs() throws IOException {
        MessageSource messageSource = MessageSource.getInstance();

        String message = messageSource.getMessage("WELCOME_USER", Locale.ENGLISH, "Alex");

        assertEquals("Hello, Alex!", message);
    }

    @Test
    void getMessageThrowsWhenKeyNotFound() throws IOException {
        MessageSource messageSource = MessageSource.getInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> messageSource.getMessage("UNKNOWN_KEY", Locale.ENGLISH)
        );
    }

    @Test
    void getMessageThrowsWhenLocaleNotFound() throws IOException {
        MessageSource messageSource = MessageSource.getInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> messageSource.getMessage("WELCOME", Locale.GERMAN)
        );
    }
}
