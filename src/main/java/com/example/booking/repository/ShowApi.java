package com.example.booking.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//
@Retention(RetentionPolicy.RUNTIME)
public @interface ShowApi {
    String value() default "";
}