package org.example.Task1;
import java.util.List;

public class ValidationException extends Exception {
    private final List<String> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = List.of(message);
    }

    public ValidationException(List<String> errors) {
        super("Ошибки валидации:\n" + String.join("\n", errors));
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}