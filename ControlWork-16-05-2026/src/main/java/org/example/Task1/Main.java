package org.example.Task1;

public class Main {
    public static void main(String[] args) {

        Validator v = new Validator();

        Address a1 = new Address("Ленина", "Москва", "123456");
        User u1 = new User("Вася", 25, "vasya@mail.ru", a1);

        try {
            v.validate(u1);
            System.out.println("OK");
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }

        Address a2 = new Address("", "", "123");
        User u2 = new User("A", 16, "bad", a2);

        try {
            v.validate(u2);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }

        User u3 = new User("Петя", 30, "petya@mail.ru", null);

        try {
            v.validate(u3);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}