package com.tfxing.persondaily.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelService {
    void testImportListener(MultipartFile file) throws IOException;

    void testExportExcel(HttpServletResponse response) throws IOException;

    void testExportPersonExcel(HttpServletResponse response) throws IOException;

    void exportDynamicExcel(HttpServletResponse response) throws Exception;

    void exportComplexExcel(HttpServletResponse response) throws Exception;
}
