package com.tfxing.persondaily.test;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        List<Employee> collect = list.stream().sorted(Comparator.comparing(Employee::getGrade, (s1,s2)->{
            if ("5".equals(s1)) {
                return -1;
            } else {
                return s1.compareTo(s2);
            }
        })
//                .thenComparing(Employee::getName, Comparator.reverseOrder())
//                .thenComparing(Employee::getName).reversed()
                )
                .collect(Collectors.toList());
        CollectionUtil.reverse(collect);
        for (Employee employee : collect) {
            System.out.println(employee);
        }
    }

    static final List<Employee> list = Arrays.asList(
        new Employee("张三", "14", "6"),
        new Employee("李四", "16", "6"),
        new Employee("王五", "12", "5")
    );

    static class Employee {
        String name;
        String age;
        String grade;

        public Employee(String name, String age, String grade) {
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    ", grade='" + grade + '\'' +
                    '}';
        }
    }
}