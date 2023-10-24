package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
@RestController
public class QRController {
    @Autowired
    QRService qrService;

    @RequestMapping("testQr")
    public void generateV3(String content, HttpServletResponse servletResponse) throws IOException {
        qrService.generateStream(content,servletResponse);
    }
}
