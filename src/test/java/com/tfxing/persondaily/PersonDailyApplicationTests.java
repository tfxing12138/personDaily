package com.tfxing.persondaily;

import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.PersonFather;
import com.tfxing.persondaily.entity.po.PersonSon;
import com.tfxing.persondaily.entity.po.User;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PersonDailyApplicationTests {

    @Test
    void contextLoads() {
        
    }
    
    @Test
    void testGeneric() throws IllegalAccessException {
        testGeneric01(new User(1L,"tfxing"));
    }

    /**
     * 泛型方法
     * @param t
     * @param <T>
     */
    public <T> void testGeneric01(T t) throws IllegalAccessException {
        Field[] allFields = t.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object vlaue = field.get(t);
            String desc = String.format("fieldName:%s -- value:%s",fieldName,vlaue);
            System.out.println(desc);
        }
    }
    
    @Test
    void testGeneric02() {
        /*List<String> strList = new ArrayList<>();
        strList.add("one");
        strList.add("two");*/
//        strList.add(1);
        
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add("one");
        System.out.println(list);
        
        List<? extends Person> plist = new ArrayList<>();
       testGeneric02(plist);
    }
    
    private void testGeneric02(List<? extends Person> list) {
        System.out.println(list);
    }
    
    @Test
    void testReflect() throws Exception {
        Class pClass = Person.class;
        Class aClass = Class.forName("com.tfxing.persondaily.entity.po.Person");

        Person person = new Person();
        Class clazz = person.getClass();
        //获取所有的字段
        Field[] allFields = clazz.getDeclaredFields();
        //获取指定的字段
        Field field = clazz.getDeclaredField("personName");
        //无视字段的访问权限
        field.setAccessible(true);
        //获取字段的名称
        String fieldName = field.getName();
        //获取字段的值，参数为获取值的指定对象
        Object value = field.get(person);
        //获取无参构造方法并创建一个实例
        Object obj = clazz.getConstructor().newInstance();
        //获取所有的方法
        Method[] allMethods = clazz.getDeclaredMethods();
        //获取指定的方法
        Method method = clazz.getDeclaredMethod("getPersonName");
        //执行方法，参数为使用方法的指定对象，该方法的参数
        method.invoke(person,"param");
        //获取所有的注解
        Annotation[] annotations = clazz.getAnnotations();
        //获取指定的注解
        Annotation annotation = clazz.getAnnotation(Data.class);

    }
    

}
