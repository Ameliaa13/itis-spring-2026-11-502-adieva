package org.example;

import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws IOException {
        MessageSource ms = MessageSource.getInstance();

        String welcome = ms.getMessage("WELCOME", Locale.ENGLISH);
        String personalWelcome = ms.getMessage("WELCOME_USER", Locale.forLanguageTag("ru"), "Amelia");

        System.out.println(welcome);
        System.out.println(personalWelcome);
    }
}
