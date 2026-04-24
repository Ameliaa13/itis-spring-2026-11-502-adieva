package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.*;
import java.lang.annotation.Annotation;


public class Main {
    public static void main(String[] args) {
        inspect(Person.class, "-", 1);

    }

    public static void inspect(Class clazz, String indent, int countOfIndent){
        if(clazz == null) throw new IllegalArgumentException("Class is null");

        System.out.println(indent.repeat(countOfIndent)+"Имя: " +clazz.getName());
        String modifier = Modifier.toString(clazz.getModifiers());
        String type = "";
        if(clazz.isInterface()){
            type = "Interface";
        }else if(clazz.isAnonymousClass()){
            type = "Anonymous";
        }else if(clazz.isEnum()){
            type = "Enum";
        } else if (clazz.isAnnotation()) {
            type = "Annotation";
        } else  {
            type = "Class";
        }

        Class<?> parent = clazz.getSuperclass();
        Class[] interfaces = clazz.getInterfaces();

        String interfacesOutput = "";
        for(Class i : interfaces){
            interfacesOutput = interfacesOutput + i.getName() + " ";
        }

        System.out.println(indent.repeat(countOfIndent)+ "Модификатор: "+modifier);
        System.out.println(indent.repeat(countOfIndent)+"Тип: " + type);
        System.out.println(indent.repeat(countOfIndent) + "Родитель: " + parent);
        System.out.println(indent.repeat(countOfIndent) + "Интерфейсы: "+ interfacesOutput);
        printAnnotation(clazz.getAnnotations(), indent, countOfIndent);

        Field[] fields = clazz.getDeclaredFields();

        System.out.println(" ");
        System.out.println(indent.repeat(countOfIndent)+"Поля: ");
        for (Field field : fields){

            String modifierOfField = Modifier.toString(field.getModifiers());
            String typeOfField = field.getType().getName();

            System.out.println(indent.repeat(countOfIndent+1) + "Имя: " + field.getName());
            printAnnotation(field.getAnnotations(), indent, countOfIndent+2);
            System.out.println(indent.repeat(countOfIndent+2) + "Модификатор: " + modifierOfField);
            System.out.println(indent.repeat(countOfIndent+2) + "Тип: " + typeOfField);
        }

        System.out.println(" ");
        System.out.println(indent.repeat(countOfIndent)+"Методы: ");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods){

            String modifierOfMethod = Modifier.toString(method.getModifiers());
            String typeOfMethod = method.getReturnType().getName();
            System.out.println(indent.repeat(countOfIndent+1) + "Имя: " + method.getName());
            printAnnotation(method.getAnnotations(), indent, countOfIndent+2);
            System.out.println(indent.repeat(countOfIndent+2) + "Модификатор: " + modifierOfMethod);
            System.out.println(indent.repeat(countOfIndent+2) + "Тип: " + typeOfMethod);

            if(method.getParameters().length != 0){
                printParameter(method.getParameters(), indent, countOfIndent+2);
            }
            printException(method.getExceptionTypes(), indent, countOfIndent+2);
        }

        System.out.println(" ");
        System.out.println(indent.repeat(countOfIndent) + "Конструкторы:");
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors){
            String modifierOfConstructor = Modifier.toString(constructor.getModifiers());
            Parameter[] parametrs = constructor.getParameters();

            System.out.println(indent.repeat(countOfIndent+1) + "Имя: " + constructor.getName());
            printAnnotation(constructor.getAnnotations(), indent, countOfIndent+2);
            System.out.println(indent.repeat(countOfIndent+2) + "Модификатор: " + modifierOfConstructor);
            if(parametrs.length !=0){
                    printParameter(parametrs, indent, countOfIndent+3);
            }
            printException(constructor.getExceptionTypes(), indent, countOfIndent+2);
        }
        Class<?>[] classes =clazz.getDeclaredClasses();
        for (Class<?> c : classes){
            System.out.println(" ");
            System.out.println("Внутренний класс:");
             inspect(c, indent, countOfIndent+1 );
        }

    }

    public static void printAnnotation(Annotation[] annotations, String indent,int countOfIndent){
        String annotationsOutput = "";
        for(Annotation a :annotations){
            annotationsOutput = annotationsOutput + a.annotationType().getName() + " ";
        }
        if(annotations.length !=0){
            System.out.println(indent.repeat(countOfIndent) + "Аннотации: " + annotationsOutput );
        }
    }

    public static void printParameter(Parameter[] parameters, String indent, int countOfIndent){

        System.out.println(indent.repeat(countOfIndent-1) + "Параметры: " );
        for(Parameter p : parameters){
            String typeOfParameter = p.getType().getName();
            String name = p.getName();

            System.out.println(indent.repeat(countOfIndent) + "Имя: " + name);
            System.out.println(indent.repeat(countOfIndent+1) + "Тип: " + typeOfParameter);
            printAnnotation(p.getAnnotations(), indent, countOfIndent+1);
        }
    }

    public static void printException(Class[] exception, String indent,int countOfIndent){
        String exOutput = "";
        for (Class ex : exception){
            exOutput = exOutput + ex + " ";
        }
        if(exception.length !=0){
            System.out.println(indent.repeat(countOfIndent) + "Исключения: " + exOutput);
        }
    }



}