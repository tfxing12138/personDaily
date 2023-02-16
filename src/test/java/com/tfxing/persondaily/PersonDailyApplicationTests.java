package com.tfxing.persondaily;

import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.PersonFather;
import com.tfxing.persondaily.entity.po.PersonSon;
import com.tfxing.persondaily.entity.po.User;
import com.tfxing.persondaily.entity.rbTree.RBNode;
import com.tfxing.persondaily.entity.rbTree.RBTree;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    
    @Test
    void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("one","one");
        map.put("one","two");
        System.out.println(map.get("one"));
        System.out.println(null == map.get("two"));
    }

    Integer size = 100;
    
    @Test
    void testThread() {
        
        new Thread(()->{
            sub(size);
        },"thread1").start();

        new Thread(()->{
            sub(size);
        },"thread1").start();
        
    }

    public void sub(int size) {
        while (size > 0) {
            size--;
            System.out.println(size);
        }
    }
    
    @Data
    class TestGenericClass01{
        int a;
        int b;
    }

    @Data
    class TestGenericClass02{
        int a;
        int c;
    }
    
    @Test
    void testGeneric03() {
        TestGenericClass01 testGenericClass01 = new TestGenericClass01();
        testGenericClass01.setA(1);
        testGenericClass01.setB(2);
        TestGenericClass02 testGenericClass02 = new TestGenericClass02();
        testGenericClass02.setA(3);
        testGenericClass02.setC(4);
        System.out.println(testGeneric0301(testGenericClass01, testGenericClass02));

    }
    
    private <T,E> E testGeneric0301(T t,E e) {
        BeanUtils.copyProperties(t,e);
        return e;
    }
    
    @Test
    void testFor() {
        int i;
        for(i=0;i<10;i++);
        System.out.println(i);
    }
    
    @Test
    void testRBTreeDel() {
        RBNode<Integer> root = new RBNode<>(211,true);
        RBNode<Integer> rootL = new RBNode<>(195,false);
        root.setLeft(rootL);
        rootL.setParent(root);
        RBNode<Integer> rootLL = new RBNode<>(159, true);
        rootL.setLeft(rootLL);
        rootLL.setParent(rootL);
        RBNode<Integer> rootLR = new RBNode<>(200, true);
        rootL.setRight(rootLR);
        rootLR.setParent(rootL);
        RBNode<Integer> rootLLL = new RBNode<>(120, false);
        rootLL.setLeft(rootLLL);
        rootLLL.setParent(rootLL);
        RBNode<Integer> rootLLR = new RBNode<>(192, false);
        rootLL.setRight(rootLLR);
        rootLLR.setParent(rootLL);
        RBNode<Integer> rootR = new RBNode<>(324, false);
        root.setRight(rootR);
        rootR.setParent(root);
        RBNode<Integer> rootRR = new RBNode<>(728, true);
        rootR.setRight(rootRR);
        rootRR.setParent(rootR);
        RBNode<Integer> rootRL = new RBNode<>(245, true);
        rootR.setLeft(rootRL);
        rootRL.setParent(rootR);
        RBNode<Integer> rootRRR = new RBNode<>(985, false);
        rootRR.setRight(rootRRR);
        rootRRR.setParent(rootRR);
        RBNode<Integer> rootRRL = new RBNode<>(643, false);
        rootRRR.setLeft(rootRRL);
        rootRRL.setParent(rootRRR);

        RBTree<Integer> tree = new RBTree<>();
        tree.setRoot(root);
        
        tree.deleteNode(root);
    }
    
    @Test
    void testReflect03() throws Exception {
        /*System.out.println("hello world");
        
        Class<System> clazz = System.class;
        Field out = clazz.getDeclaredField("out");
        out.setAccessible(true);
        Object outObj = out.get(null);
        Class outClass = outObj.getClass();
        Method println = outClass.getDeclaredMethod("println");
        println.invoke(null,"hello world");*/

        /*User user = new User();
        user.setUserName("tfxing");
        Person person = new Person();
        person.setPersonName("personNameTfxing");

        Class<? extends Person> personClass = person.getClass();
        Field userField = personClass.getDeclaredField("user");
        userField.setAccessible(true);
        Object obj = userField.get(null);
        Class<?> userClass = obj.getClass();
        Method getUserName = userClass.getDeclaredMethod("getUserName");
        getUserName.setAccessible(true);
        Object invoke = getUserName.invoke(user);
        System.out.println(invoke.toString());

        Method staticMethod = userClass.getDeclaredMethod("staticMethod");
        staticMethod.invoke(null);*/

        Class<?> clazz = Class.forName("java.io.PrintStream");
        Method method = clazz.getMethod("println", String.class);
        Object out = System.out;
        method.invoke(out, "Hello World!");
    }
}
