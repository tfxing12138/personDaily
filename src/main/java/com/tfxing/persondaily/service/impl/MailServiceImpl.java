package com.tfxing.persondaily.service.impl;

import com.tfxing.persondaily.service.MailService;
import com.tfxing.persondaily.utils.DateUtils;
import com.tfxing.persondaily.utils.EmailUtil;
import org.assertj.core.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailServiceImpl implements MailService {


    @Override
    public void sendMail() throws Exception {
        String subject = "《塞尔达传说王国之泪》倒计时";
        Long dayCount = DateUtils.subDayCount(DateUtils.str2Date("2023-05-12","yyyy-MM-dd"));
        String content = "<h1>《塞尔达传说 王国之泪》</h1> \n" +
                "距离《塞尔达传说 王国之泪》发售，还剩【%s】天!";
        if(dayCount == 1) {
            content = "<h1>《塞尔达传说 王国之泪》</h1> \n" +
                    "《塞尔达传说 王国之泪》今日发售!";
        } else if(dayCount < 1) {
            return;
        }
        content = String.format(content,dayCount);
        EmailUtil.sendMail(subject,content);
    }

    @Override
    public void sendMailFinalDay() throws Exception {
        String subject = "《塞尔达传说王国之泪》倒计时";
        Integer hoursCount = DateUtils.subHoursCount();
        String content = "<h1>《塞尔达传说 王国之泪》</h1> \n" +
                "距离《塞尔达传说 王国之泪》发售，还剩【%s】小时!";
        if(hoursCount == 0) {
            content = "<h1>《塞尔达传说 王国之泪》</h1> \n" +
                    "《塞尔达传说 王国之泪》解禁!";
        }
        if(hoursCount < 0) {
            return;
        }
        content = String.format(content,hoursCount);
        EmailUtil.sendMail(subject,content);
    }
}
