package com.tfxing.persondaily.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import com.tfxing.persondaily.entity.testDemo.Person;

/**
 * dbf 工具
 * 
 * @author yushen
 *
 */
public class DBFUitl {

  public static void main(String args[]) throws DBFException, IOException, IllegalAccessException {
    List<HashMap<String,Object>> listdata = new ArrayList<HashMap<String,Object>>();
    HashMap<String,Object>  m = new HashMap<String,Object>();
    m.put("a", "1");
    m.put("b", "1");
    m.put("c", "1");
    HashMap<String,Object>  m2 = new HashMap<String,Object>();
    m2.put("a", "1");
    m2.put("b", "1");
    m2.put("c", "1");
    listdata.add(m2);
    listdata.add(m);
    
//    exportdbf("kingdee.dbf",listdata);

    exportdbf("kingdee.dbf", Arrays.asList(new Person("张麻子","深圳", "18"),new Person("王二狗","上海", "20")));
     
  }

  /**
   * list 生成 dbf 
   * 
   * @param dbfname 文件 名
   * @param listdata  文件源数据
   *
   * @throws IOException
   */
  /*public static void exportdbf(String dbfname,List<HashMap<String,Object>> listdata) throws IOException {

    int i2 = 0;
    for(String key : listdata.get(0).keySet()) {
      i2++;
    }
    DBFField fields[] = new DBFField[i2];

    int i = 0;
    for(String key : listdata.get(0).keySet()) {
      fields[i] = new DBFField();
      fields[i].setName(key);
      fields[i].setType(DBFDataType.CHARACTER);
      fields[i].setLength(100);
      i++;
    }
     
    FileOutputStream fos = new FileOutputStream(dbfname);
    DBFWriter writer = new DBFWriter(fos);
    writer.setFields(fields);


    for (int j = 0; j < listdata.size(); j++) {
//      HashMap<String,Object> m3 = listdata.get(j);
      Object rowData[] = new Object[i];
      int i1 = 0;
      for(String key : listdata.get(j).keySet()) {
        rowData[i1] = listdata.get(j).get(key);
        i1++;
      }
      writer.addRecord(rowData);
    }
    
    writer.write(fos);
    fos.close();
    System.out.println("dbf文件生成！");
  }*/

  /**
   * list 生成 dbf
   *
   * @param dbfname 文件 名
   * @param listdata  文件源数据
   *
   * @throws IOException
   */
  public static void exportdbf(String dbfname,List<Person> listdata) throws IOException, IllegalAccessException {

    Class<Person> clazz = Person.class;
    Field[] allField = clazz.getDeclaredFields();

    int i2 = allField.length;

    DBFField fields[] = new DBFField[i2];

    int i = 0;
    for (Field field : allField) {
      field.setAccessible(true);
      String name = field.getName();

      fields[i] = new DBFField();
      fields[i].setName(name);
      fields[i].setType(DBFDataType.CHARACTER);
      fields[i].setLength(100);
      i++;
    }

    FileOutputStream fos = new FileOutputStream(dbfname);
    DBFWriter writer = new DBFWriter(fos);
    writer.setCharset(Charset.forName("GBK"));
    writer.setFields(fields);


    for (Person data : listdata) {

      Object rowData[] = new Object[i];
      int i1 = 0;
      for (Field field : allField) {
        field.setAccessible(true);

        rowData[i1] = field.get(data);
        i1++;
      }
      writer.addRecord(rowData);
    }

    writer.write(fos);
    fos.close();
    System.out.println("dbf文件生成！");
  }
}