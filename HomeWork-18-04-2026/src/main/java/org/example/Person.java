package org.example;

import java.util.List;
import java.util.ArrayList;

/**
 * Пример класса для тестирования Reflection API
 */

@MyAnnotation
public class Person {

    // Поля класса
    private String name;
    private int age;
    protected String email;
    public static int personCount = 0;
    private final int ID;
    private List<String> hobbies;

    // Статический блок
    static {
        personCount = 0;
        System.out.println("Статический блок инициализации");
    }

    // Конструктор 1
    @MyAnnotation
    public Person() {
        this.ID = generateId();
        this.name = "Unknown";
        this.age = 0;
        this.hobbies = new ArrayList<>();
        personCount++;
    }

    // Конструктор 2
    public Person(String name, int age) {
        this.ID = generateId();
        this.name = name;
        this.age = age;
        this.hobbies = new ArrayList<>();
        personCount++;
    }

    // Конструктор 3 (с исключением)
    public Person(String name, int age, String email) throws IllegalArgumentException {
        this.ID = generateId();
        this.name = name;
        this.age = age;
        this.email = email;
        this.hobbies = new ArrayList<>();

        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Некорректный возраст: " + age);
        }
        personCount++;
    }

    // Методы
    @MyAnnotation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws IllegalArgumentException {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Некорректный возраст: " + age);
        }
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public static int getPersonCount() {
        return personCount;
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    // Приватный метод
    private int generateId() {
        return (int) (Math.random() * 10000);
    }

    // Метод с исключением
    public void validateAge() throws IllegalStateException {
        if (age < 18) {
            throw new IllegalStateException("Person is under 18 years old");
        }
    }

    @Override
    public String toString() {
        return String.format("Person{ID=%d, name='%s', age=%d, email='%s'}",
                ID, name, age, email);
    }

    // ВНУТРЕННИЙ КЛАСС
    public class Address {
        private String street;
        private String city;
        private int zipCode;

        public Address(String street, String city, int zipCode) {
            this.street = street;
            this.city = city;
            this.zipCode = zipCode;
        }

        public String getStreet() { return street; }
        public String getCity() { return city; }
        public int getZipCode() { return zipCode; }

        public void setStreet(String street) { this.street = street; }
        public void setCity(String city) { this.city = city; }
        public void setZipCode(int zipCode) { this.zipCode = zipCode; }

        @Override
        public String toString() {
            return street + ", " + city + ", " + zipCode;
        }
    }

    // СТАТИЧЕСКИЙ ВНУТРЕННИЙ КЛАСС
    public static class Company {
        private String companyName;
        private String position;

        public Company(String companyName, String position) {
            this.companyName = companyName;
            this.position = position;
        }

        public String getCompanyName() { return companyName; }
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }

        public void display() {
            System.out.println("Works at " + companyName + " as " + position);
        }
    }

    // ENUM (внутренний)
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    // Интерфейс внутри класса
    public interface Greetable {
        void greet();
    }
}

// Дополнительный класс для тестирования
class Student extends Person {
    private String studentId;
    private String major;

    public Student(String name, int age, String studentId, String major) {
        super(name, age);
        this.studentId = studentId;
        this.major = major;
    }

    public String getStudentId() { return studentId; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public void study() {
        System.out.println(getName() + " is studying " + major);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Student{id=%s, major=%s}", studentId, major);
    }
}

