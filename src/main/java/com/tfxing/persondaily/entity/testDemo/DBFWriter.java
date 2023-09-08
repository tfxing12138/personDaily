package com.tfxing.persondaily.entity.testDemo;

import org.xBaseJ.*;
import org.xBaseJ.fields.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBFWriter {

    public static void main(String[] args) {
        List<Person> dataList = Arrays.asList(new Person("link","深圳","18"),new Person("yuki","上海","19"));
        listToDBF(dataList,"output.dbf");
    }

    public static void listToDBF(List<Person> dataList, String dbfFileName) {
        try {
            DBF dbfFile = new DBF(dbfFileName, true);
            dbfFile.setEncodingType("GBK");
            CharField nameField = new CharField("name", 100);
            CharField addressField = new CharField("address", 500);
            NumField ageField = new NumField("age", 3, 0);

            dbfFile.addField(nameField);
            dbfFile.addField(addressField);
            dbfFile.addField(ageField);

            for (Person person : dataList) {
                nameField.put(person.getName());
                addressField.put(person.getAddress());
                ageField.put(person.getAge());

                dbfFile.write();
            }

            dbfFile.close();

        } catch (xBaseJException | IOException e) {
            e.printStackTrace();
        }
    }
}
