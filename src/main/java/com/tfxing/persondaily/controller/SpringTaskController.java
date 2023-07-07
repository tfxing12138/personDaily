package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class SpringTaskController {

    @Autowired
    private MailService mailService;

//    @Scheduled(cron = "0 0 12 * * ?")
    @GetMapping("/test")
    public void sendMail() throws Exception {
        mailService.sendMail();
    }

//    @Scheduled(cron = "0 0 14,15,16,17,18,19,20,21,22,23 12 5 ? ")
    @GetMapping("/test2")
    public void sendMailFinalDay() throws Exception {
        mailService.sendMailFinalDay();
    }
}
