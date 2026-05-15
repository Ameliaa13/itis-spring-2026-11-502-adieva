package ru.itis.security;

import ru.itis.SecretCodeService;

public class SecretCodeServiceImpl implements SecretCodeService {

    private static final String CODE;

    static {
        CODE = "3bc46d23";
    }

    @Override
    public String getSecretCode() {
        return CODE;
    }
}
