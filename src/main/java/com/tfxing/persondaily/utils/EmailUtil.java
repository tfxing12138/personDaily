package com.tfxing.persondaily.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @Description TODO
 * @Author peiyu
 * @Date 2019/8/12 9:29
 */
@Component
public class EmailUtil {
    /**
     * 发件人 账号和密码
     **/
//    @Value("${mail.user}")
    public static String MY_EMAIL_ACCOUNT = "t18370240624@163.com";
    // 密码,是你自己的设置的授权码,
//    @Value("${mail.password}")
    public static String MY_EMAIL_PASSWORD = "VQMXDDHSNABKUEEI";

    // SMTP服务器(这里用的163 SMTP服务器)
//    @Value("${mail.host}")
    public static String MEAIL_163_SMTP_HOST = "smtp.163.com";
    /**
     * 端口号,这个是163使用到的;QQ的应该是465或者875
     **/
    public static final String SMTP_163_PORT = "25";

    /**
     * 收件人
     **/
    public static final String RECEIVE_EMAIL_ACCOUNT = "2867253802@qq.com,2653673599@qq.com";

    public static void sendMail(String subject, String content) throws Exception {
        Properties p = new Properties();
        p.setProperty("mail.smtp.host", MEAIL_163_SMTP_HOST);
        p.setProperty("mail.smtp.port", SMTP_163_PORT);
        p.setProperty("mail.smtp.socketFactory.port", SMTP_163_PORT);
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.socketFactory.class", "SSL_FACTORY");

        Session session = Session.getInstance(p, new Authenticator() {
            // 设置认证账户信息
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);
            }
        });
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        // 发件人
        message.setFrom(new InternetAddress(MY_EMAIL_ACCOUNT));
        Address[] tos = createAddressList(RECEIVE_EMAIL_ACCOUNT);
        // 收件人和抄送人
        message.setRecipients(Message.RecipientType.TO, tos);

        // 有些敏感信息会被邮件服务器禁止发送，所以尽量多测试下
        message.setSubject(subject);
        message.setContent(content,
                "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        Transport.send(message);
    }

    private static Address[] createAddressList(String recipients) throws AddressException {
        InternetAddress[] addressesTo = null;
        if (recipients != null && recipients.trim().length() > 0) {
            String[] receiveList = recipients.split(",");
            int receiveCount = receiveList.length;
            if (receiveCount > 0) {
                addressesTo = new InternetAddress[receiveCount];
                for (int i = 0; i < receiveCount; i++) {
                    addressesTo[i] = new InternetAddress(receiveList[i]);
                }
            }
        }
        return addressesTo;
    }
}