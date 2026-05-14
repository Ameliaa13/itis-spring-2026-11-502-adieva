package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;

//мета-анотация
@Retention(RetentionPolicy.RUNTIME)

public @interface MyAnnotation {
    String value() default "default";
}
