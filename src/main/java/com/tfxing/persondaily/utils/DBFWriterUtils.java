package com.tfxing.persondaily.utils;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
 
/**
 * @author ：
 * @date ：Created in 2021-7-8 14:33
 * @description：
 * @modified By：
 * @version:
 */
public class DBFWriterUtils {
 
    public static void writeDbf(List beans, String[][] columnNames, String dbfName) {
        try {
            File file = new File(dbfName);
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(dbfName+File.separator+"GD.DBF");
            // 每行返回的属性有哪些
            List propertys = writeLine(beans.get(0), columnNames[1]);
            // 获取中文标题
            String[] columnList = columnNames[0];
            List propertyStrList = new ArrayList();
            for (String val : columnList) {
                propertyStrList.add(val);
            }
            Charset charset = Charset.forName("GBK");
            DBFWriter writer = new DBFWriter(fos, charset);
            //设置写入文件字段类型
            DBFField[] dbffs = writeFields(beans.get(0), propertys, propertyStrList);
            writer.setFields(dbffs);
            for (int i = 0; i < beans.size(); i++) {
                writer.addRecord(writeLine(beans.get(i), propertys));
 
            }
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
    /**
     * 设置写入dbf文件字段类型
     *
     * @return
     */
 
    private static DBFField[] writeFields(Object obj, List propertys, List propertyStrList) throws UnsupportedEncodingException {
        java.lang.reflect.Field[] field = obj.getClass().getDeclaredFields();
 
        DBFField[] fields = new DBFField[propertys.size()];
        int i = 0;
        for (int j = 0; j < propertys.size(); j++) {
            String properStr = propertys.get(j).toString().toUpperCase();
            properStr = properStr.length() > 10 ? properStr.substring(properStr.length() - 10, properStr.length()) : properStr;
            for (java.lang.reflect.Field tempF : field) {
                String name = tempF.getName().toUpperCase();
                name = name.length() > 10 ? name.substring(name.length() - 10, name.length()) : name;
                
                if (name.equals(properStr)) {
                    fields[i] = new DBFField();
                    String str = propertyStrList.get(j).toString() == null ? new String(" ") : propertyStrList.get(j).toString();
                    fields[i].setName(str);
                    if (tempF.getType().equals(BigDecimal.class)) {
                        fields[i].setType(DBFDataType.FLOATING_POINT);
                        fields[i].setLength(22);
                        fields[i].setDecimalCount(2);
                    } else {
                        fields[i].setType(DBFDataType.CHARACTER);
                        fields[i].setLength(254);
                    }
                    i++;
                }
            }
 
        }
        return fields;
    }
 
    /**
     * 返回每行匹配配置xml的数?
     *
     * @param bean
     * @param propertys
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
 
    private static Object[] writeLine(Object bean, List propertys) {
        List list = new ArrayList();
 
        printObj(bean, list, propertys);
 
        return list.toArray();
 
    }
 
    public static void printObj(Object obj, List list, List propertys) {
        Method[] meth = obj.getClass().getMethods();
        int i = 0;
        // 这里稍微繁琐了点儿，可以去掉截取字符串，因为这是旧版留下来的，现在不需要
        for (int j = 0; j < propertys.size(); j++) {
 
            String properStr = propertys.get(j).toString().toUpperCase();
            properStr = properStr.length() > 10 ? properStr.substring(properStr.length() - 10, properStr.length()) : properStr;
 
            for (Method tempM : meth) {//这里可以看出，你的实体属性不能有class开头
                if (tempM.getName().indexOf("get") > -1 && tempM.getName().indexOf("getClass") == -1) {
                    String str = tempM.getName().toString().substring(3, tempM.getName().toString().length()).toUpperCase();
                    str = str.length() > 10 ? str.substring(str.length() - 10, str.length()) : str;
                    if (str.equals(properStr)) {
                        try {
                            Object objVaule = tempM.invoke(obj);
                            if (tempM.getReturnType().equals(BigDecimal.class)) {
                                BigDecimal bd = new BigDecimal(objVaule.toString());
                                list.add(bd.doubleValue());
 
                            } else {
                                if (objVaule == null) {
                                    list.add(objVaule);
                                } else {
                                    String value = objVaule.toString().length() > 254 ? objVaule.toString().substring(0, 254) : objVaule.toString();
                                    try {
                                        list.add(value.trim());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            i++;
                            break;
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
 
                        }
                    }
                }
            }
 
        }
 
    }
 
    /**
     * 返回每行匹配配置xml的数�?
     *
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
 
    public static List writeLine(Object obj, String[] columnNames) {
        List list = new ArrayList();
 
        for (String str : columnNames) {
            list.add(str);
        }
        return list;
 
    }

    public static void main(String[] args) {
        String[] column_title = {"年度","姓名","存活时间"};
        String[] column_name = {"year","name","survivalTime"};// 这里是实体属性
        String[][] columnNames = {column_title , column_name };
        List beans = new ArrayList();// 把实体转化为List
        String path="DBF.dbf";
        DBFWriterUtils.writeDbf(beans,columnNames ,path);
    }
}