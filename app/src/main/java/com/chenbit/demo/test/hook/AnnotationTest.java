package com.chenbit.demo.test.hook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by NSKY on 2018/11/9.
 */
@Target({ElementType.TYPE,ElementType.METHOD, ElementType.FIELD})
public @interface AnnotationTest {
    String value();
    int age();
}
