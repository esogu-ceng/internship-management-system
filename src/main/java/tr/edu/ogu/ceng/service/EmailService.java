package tr.edu.ogu.ceng.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dto.EmailReceiverDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static tr.edu.ogu.ceng.enums.UserType.*;

@Service
public class EmailService {

    @Autowired
    private SettingService settingService;





    public  void sendEmail(String sendTo,String subject,String message) {
        try {
            JavaMailSender mailSender = getJavaMailSender();
            MimeMessage mimeMessage= mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setTo(sendTo);
            messageHelper.setSubject(subject);
            mimeMessage.setText(message,"UTF-8","HTML");
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }




    // Method to get JavaMailSender instance
    private JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(settingService.findValueByKey("mail_host"));
        mailSender.setPort(Integer.parseInt(settingService.findValueByKey("mail_port")));
        mailSender.setUsername(settingService.findValueByKey("mail_username"));
        mailSender.setPassword(settingService.findValueByKey("mail_password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}


