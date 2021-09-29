package dev.isnow.quick.check.api;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CheckInfo {
    String name();
    String type();
    String description();

    boolean experimental() default false;
}
