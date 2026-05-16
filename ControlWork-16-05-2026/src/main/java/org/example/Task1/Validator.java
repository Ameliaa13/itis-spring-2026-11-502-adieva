package org.example.Task1;

import java.util.*;
import java.lang.reflect.*;

import static java.lang.reflect.Modifier.*;

public class Validator {

    public void validate(Object obj) throws ValidationException {
        if (obj == null) {
            throw new ValidationException(List.of("Объект null"));
        }

        List<String> allErrors = new ArrayList<>();

        try {
            checkObject(obj, allErrors, "");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Ошибка доступа к полю: " + e.getMessage(), e);
        }

        if (!allErrors.isEmpty()) {
            throw new ValidationException(allErrors);
        }
    }

    private void checkObject(Object obj, List<String> errors, String path)
            throws IllegalAccessException {

        if (obj == null) return;

        Class<?> currentClass = obj.getClass();

        while (currentClass != null && currentClass != Object.class) {

            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                int mod = field.getModifiers();
                if (isStatic(mod) || isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                Object value = field.get(obj);
                String fieldName = path.isEmpty() ? field.getName() : path + "." + field.getName();

                checkField(field, value, fieldName, errors);

                if (field.isAnnotationPresent(Valid.class)) {
                    if (value == null) {
                        errors.add(fieldName + " null");
                    } else {
                        checkObject(value, errors, fieldName);
                    }
                }
            }

            currentClass = currentClass.getSuperclass();
        }
    }

    private void checkField(Field field, Object value, String fieldName, List<String> errors) {

        if (field.isAnnotationPresent(NotNull.class)) {
            if (value == null) {
                errors.add(fieldName + " null");
                return;
            }
        }

        if (value == null) return;

        if (field.isAnnotationPresent(Min.class)) {
            Min minAnno = field.getAnnotation(Min.class);
            if (isNumber(value)) {
                int number = getIntValue(value);
                if (number < minAnno.value()) {
                    errors.add(fieldName + " >= " + minAnno.value() + " need " + number);
                }
            } else {
                System.err.println(fieldName + " не число");
            }
        }

        if (field.isAnnotationPresent(Max.class)) {
            Max maxAnno = field.getAnnotation(Max.class);
            if (isNumber(value)) {
                int number = getIntValue(value);
                if (number > maxAnno.value()) {
                    errors.add(fieldName + " <= " + maxAnno.value() + " need " + number);
                }
            } else {
                System.err.println(fieldName + " не число");
            }
        }

        if (field.isAnnotationPresent(Size.class)) {
            Size sizeAnno = field.getAnnotation(Size.class);
            int size = -1;

            if (value instanceof String) {
                size = ((String) value).length();
            } else if (value instanceof Collection) {
                size = ((Collection<?>) value).size();
            }

            if (size != -1) {
                if (size < sizeAnno.min() || size > sizeAnno.max()) {
                    errors.add(fieldName + " size " + size + " need " + sizeAnno.min() + "-" + sizeAnno.max());
                }
            } else {
                System.err.println(fieldName + " не строка и не список");
            }
        }

        if (field.isAnnotationPresent(Email.class)) {
            String email = value.toString();
            if (!isValidEmail(email)) {
                errors.add(fieldName + " неверный email: " + email);
            }
        }
    }

    private boolean isNumber(Object obj) {
        return obj instanceof Integer || obj instanceof Long ||
                obj instanceof Double || obj instanceof Float ||
                obj instanceof Byte || obj instanceof Short;
    }

    private int getIntValue(Object number) {
        if (number instanceof Integer) return (Integer) number;
        if (number instanceof Long) return ((Long) number).intValue();
        if (number instanceof Double) return ((Double) number).intValue();
        if (number instanceof Float) return ((Float) number).intValue();
        if (number instanceof Byte) return (Byte) number;
        if (number instanceof Short) return (Short) number;
        return 0;
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        if (email.contains(" ")) return false;

        int atIndex = email.indexOf('@');
        if (atIndex <= 0) return false;

        String afterAt = email.substring(atIndex + 1);
        int dotIndex = afterAt.indexOf('.');
        if (dotIndex <= 0 || dotIndex >= afterAt.length() - 1) return false;

        return true;
    }
}