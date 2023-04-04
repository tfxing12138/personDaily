package com.tfxing.persondaily.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {
    void testImportListener(MultipartFile file) throws IOException;
}
