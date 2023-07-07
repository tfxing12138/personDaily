package com.tfxing.persondaily.test;

import com.tfxing.persondaily.entity.po.GenericClass;
import lombok.Data;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Date;

@SpringBootTest
public class GenericTest {

    @Test
    public void testGeneric1() throws Exception {
        Class<GenericClass> clazz = GenericClass.class;

        Constructor<GenericClass> defaultConstructor = clazz.getDeclaredConstructor(Boolean.class);
        GenericClass genericClass = defaultConstructor.newInstance(true);

        System.out.println(genericClass);
    }


    @Test
    public void testGeneric2() throws Exception {
        Class<GenericClass> clazz = GenericClass.class;
        Field classNameField = clazz.getDeclaredField("className");
        classNameField.setAccessible(true);

        Class<?> type = classNameField.getType();
        System.out.println(type.getSimpleName());

        System.out.println(type.getName());

        System.out.println(type.getTypeName());
    }
}
