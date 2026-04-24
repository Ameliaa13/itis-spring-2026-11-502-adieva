package org.example;

import java.util.Comparator;

@MyAnnotation
public class Person1 implements Comparator, Comparable {
    private String name;
    private int age;

    public Person1(){}

    public Person1(@MyAnnotation String name, int age){

        this.name = name;
        this.age = age;
    }


    public static void printHello(){
        System.out.println("Hello! ");
    }

    public String getName() {
        return name;
    }

    @MyAnnotation(value = "Strochka")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    @MyAnnotation
    public void setAge(int age) {
        this.age = age;
    }


    void print() {
        System.out.printf("Person. Name: %s;  Age: %d\n", name, age);
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
