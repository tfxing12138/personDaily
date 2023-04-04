package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/testImportListener")
    public void testImportListener(@RequestBody MultipartFile file) throws Exception {
        if(null == file) {
            throw new RuntimeException("附件不能为空");
        }
        excelService.testImportListener(file);
    }
}
