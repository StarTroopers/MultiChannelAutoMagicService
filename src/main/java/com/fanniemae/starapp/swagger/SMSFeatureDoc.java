package com.fanniemae.starapp.swagger;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SMSFeatureDoc {

    String value();
}
