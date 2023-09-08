package com.tfxing.persondaily.entity.testDemo;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

public class DBFExporter {
    public static void main(String[] args) {
        // 创建DBF文件对象
        DBFWriter writer = null;
        try {
            writer = new DBFWriter(new FileOutputStream("output.dbf"), Charset.forName("GBK"));

            // 定义字段
            DBFField[] fields = new DBFField[3];
            fields[0] = new DBFField();
            fields[0].setName("id");

            fields[1] = new DBFField();
            fields[1].setName("name");

            fields[2] = new DBFField();
            fields[2].setName("date");

            // 添加字段到DBF文件
            writer.setFields(fields);

            // 添加数据记录
            Object[] row = new Object[3];
            row[0] = 1; // id
            row[1] = "张三"; // name
            row[2] = new Date(); // date
            writer.addRecord(row);

            row[0] = 2; // id
            row[1] = "张麻子"; // name
            row[2] = new Date(); // date
            writer.addRecord(row);

            // 关闭DBF文件
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}