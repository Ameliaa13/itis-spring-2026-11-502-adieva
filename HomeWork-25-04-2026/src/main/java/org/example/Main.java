package org.example;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Letter letter = new Letter("Привет", 1, new Address("Москва", 15));

        System.out.println("Оригинал: " + letter.getTitle() + ", " +
                letter.getPriority() + ", " +
                letter.getAddress().getCity() + " " +
                letter.getAddress().getHouseNumber());

        Map<String, Object> map = objectToMap(letter);
        System.out.println("Мапа: " + map);

        Letter restored = mapToObject(map, Letter.class);
        System.out.println("Восстановлен: " + restored.getTitle() + ", " +
                restored.getPriority() + ", " +
                restored.getAddress().getCity() + " " +
                restored.getAddress().getHouseNumber());

    }


    public static <T> Map<String, Object> objectToMap(T object) throws IllegalAccessException {
        if(object==null){
            return null;
        }
        Map<String, Object> result = new HashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(object);

            result.put(fieldName, !isSimple(fieldValue) ? objectToMap(fieldValue) : fieldValue);
        }
        return result;
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> objectClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(map == null || objectClass == null){
            return null;
        }

        T instance = objectClass.getDeclaredConstructor().newInstance();
        Field[] fields = objectClass.getDeclaredFields();

        for(Field field: fields){
            field.setAccessible(true);
            Object value = map.get(field.getName());

            if(value != null){
                if(value instanceof Map ){
                    field.set(instance, mapToObject((Map<String, Object>) value, field.getType()));
                }else {
                    field.set(instance, value);
                }
            }
        }
        return instance;
    }


    private static boolean isSimple(Object value){
        if (value == null) return true;  // null считаем простым
        Class<?> type = value.getClass();
        return type.isPrimitive()
                || type.equals(String.class)
                || type.isEnum()
                || type.equals(Boolean.class)
                || type.equals(Character.class)
                || Number.class.isAssignableFrom(type)
                || type.equals(java.util.Date.class)
                || type.equals(java.time.LocalDate.class)
                || type.equals(java.time.LocalDateTime.class)
                || (value instanceof Collection)
                || (value instanceof Map);
    }

    public static class Address {
        private String city;
        private int houseNumber;

        public Address(){ }
        public Address(String city, int houseNumber) {
            this.city = city;
            this.houseNumber = houseNumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(int houseNumber) {
            this.houseNumber = houseNumber;
        }
    }

    public static class Letter {
        private String title;
        private Address address;
        private int priority;

        public Letter(){

        }

        public Letter(String title, int priority, Address address) {
            this.title = title;
            this.priority = priority;
            this.address = address;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

}