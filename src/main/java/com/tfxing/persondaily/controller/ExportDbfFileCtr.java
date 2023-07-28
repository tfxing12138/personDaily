package com.tfxing.persondaily.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.tfxing.persondaily.utils.DBFUitl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出dbf文件
 * 
 * @author yushen
 *
 */
@RestController
public class ExportDbfFileCtr {


    @GetMapping(value = "/export/dbf/{tables}/{page}/{pageSize}")
    public void getUserInfoEx(HttpServletResponse response) throws Exception {

        String tables = "kingdee.dbf";

        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String FilePath = jarFile.getParentFile().toString() + "/zorefile/";
        File fileDir = new File(FilePath);
        if (!fileDir.exists()) {
            try {
                // 按照指定的路径创建文件夹
                fileDir.setWritable(true, false);
                fileDir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        DBFUitl.exportdbf(FilePath+tables+".dbf",null);

        retrunfile(FilePath,tables+".dbf",response);

        delFile(FilePath+tables+".dbf");

    }
    
    protected void retrunfile(String path, String filename,HttpServletResponse response) throws Exception {
        response.setContentType("application/dbf");
        response.setHeader("Location", filename);
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new FileInputStream(path+filename);
        byte[] buffer = new byte[1024];
        int i = -1;
        while ((i = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, i);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        outputStream = null;
    }
    static boolean delFile(String pathfile) {
        File file = new File(pathfile);
        if (!file.exists()) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
//                delFile(f);
            }
            return file.delete();
        }
    }
}