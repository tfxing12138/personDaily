package com.tfxing.persondaily.controller;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import com.tfxing.persondaily.entity.testDemo.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xBaseJ.DBF;
import org.xBaseJ.fields.CharField;
import org.xBaseJ.fields.NumField;
import org.xBaseJ.xBaseJException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DownloadController {

    @GetMapping("/download")
    public void downloadDbfFile(HttpServletResponse response) throws IOException, xBaseJException {
        try {


            List<Person> dataList = Arrays.asList(new Person("王思聪", "shenzhen", "18"), new Person("罗永浩", "shanghai", "19"));
            Path tempPath = Paths.get("temp.dbf");

            CharField nameField = new CharField("name", 100);
            CharField addressField = new CharField("address", 800);
            NumField ageField = new NumField("age", 3, 0);
            DBF writer = new DBF(tempPath.toString(), true);

            writer.setEncodingType("GBK");
            writer.addField(nameField);
            writer.addField(addressField);
            writer.addField(ageField);

            for (Person person : dataList) {
                nameField.put(person.getName());
                addressField.put(person.getAddress());
                ageField.put(person.getAge());
                writer.write(true);
            }

            writer.close();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment; filename=kingdee.dbf");

            try (ServletOutputStream outputStream = response.getOutputStream();
                 InputStream inputStream = Files.newInputStream(tempPath)) {

                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Files.delete(tempPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] exportDBF(List<Person> dataList) throws Exception {
        Path tempPath = Paths.get("temp.dbf");

        CharField nameField = new CharField("name", 100);
        CharField addressField = new CharField("address", 800);
        NumField ageField = new NumField("age", 3, 0);
        DBF writer = new DBF(tempPath.toString(), true);

        writer.setEncodingType("GBK");
        writer.addField(nameField);
        writer.addField(addressField);
        writer.addField(ageField);

        for (Person person : dataList) {
            nameField.put(person.getName());
            addressField.put(person.getAddress());
            ageField.put(person.getAge());
            writer.write(true);
        }

        writer.close();

        byte[] bytes = Files.readAllBytes(tempPath);
        Files.delete(tempPath);

        return bytes;
    }
}
