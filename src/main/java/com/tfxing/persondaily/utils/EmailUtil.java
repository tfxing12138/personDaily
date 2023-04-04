package com.tfxing.persondaily.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
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
        sendMail(subject, content, RECEIVE_EMAIL_ACCOUNT);
    }

    public static void sendMail(String subject, String content, String receive) throws Exception {
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
        MimeMessage message = getMessage(session, receive, subject, content);

        Transport.send(message);
    }

    private static MimeMessage getMessage(Session session, String receive, String subject, String content) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // 发件人
        message.setFrom(new InternetAddress(MY_EMAIL_ACCOUNT));
        // 收件人和抄送人
        message.setRecipients(Message.RecipientType.TO, createAddressList(receive));
        // 邮件主题
        message.setSubject(subject,"UTF-8");

        // 5. 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh = new DataHandler(new FileDataSource("src/main/resources/系统触发需求分析.png"));
        // 将图片数据添加到"节点"
        image.setDataHandler(dh);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image.setContentID("mailTestPic");

        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent(content + "</br>" + "<img src='cid:mailTestPic'/>","text/html;charset=UTF-8");
//        text.setContent(content, "text/html;charset=UTF-8");

        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");    // 关联关系

        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.setSubType("mixed");         // 混合关系

        // 有些敏感信息会被邮件服务器禁止发送，所以尽量多测试下

        message.setContent(mm, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
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