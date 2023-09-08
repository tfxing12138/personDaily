package com.tfxing.persondaily.entity.testDemo;

import lombok.Data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Data
public class Person {
    private String name;
    private String address;
    private String age;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;

    }

    public Person(String name, String address, String age) {
        this.name = name;
        this.address = address;
        this.age = age;

    }
    // 其他属性和构造方法省略

    // Getters and setters

    public static void main(String[] args) throws IOException {
        // 假设已经有一个List<Person>的数据
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Alice", "25"));
        personList.add(new Person("Bob", "30"));
        // 添加更多Person对象

        // 指定DBF文件的路径
        String dbfFilePath = "path/to/your/dbf/file.dbf";


        System.out.println("导出DBF文件成功！");
    }
}
