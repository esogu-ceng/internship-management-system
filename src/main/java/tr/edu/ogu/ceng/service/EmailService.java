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

    @Autowired
    private UserService userService;


    private static Cache<String, String> resetRequestCache = CacheBuilder.newBuilder().maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES).build();
    public  boolean sendPasswordViaEmail(User user,String name,String surname) {
        try {
            // Get JavaMailSender instance
            JavaMailSender mailSender = getJavaMailSender();

            // Create MimeMessage
            MimeMessage message = mailSender.createMimeMessage();

            // Create MimeMessageHelper for easier email creation with UTF-8 character encoding
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(user.getEmail());
            helper.setSubject("Password Reminder");

            // Customize email body based on user type
            String userTypeMessage = "";
            String additionalMessage = "";

            switch(user.getUserType()) {
                case STUDENT:

                    additionalMessage = "As a student,------";
                    break;
                case COMPANYSUPERVISOR:

                    additionalMessage = "As a company supervisor, ------";
                    break;
                case FACULTYSUPERVISOR:

                    additionalMessage = "As a faculty supervisor, ----";
                    break;
                case ADMIN:

                    additionalMessage = "As an admin, -----";
                    break;
                default:

                    additionalMessage = "Please ensure the security of your account credentials.";
                    break;
            }

            // Constructing personalized message body
            String emailBody = "<html><body>" +
                    "<div style=\"background-color: #f2f2f2; padding: 20px; border-radius: 10px;\">" +
                    "<p style=\"font-size: 16px;\">" + "Hello"+" " + name+ " " +surname+ " "+ ",<br><br>" +
                    "Your account has been successfully created. Below are your account details:<br><br>" +
                    "<strong>Username:</strong> " + user.getUsername() + "<br>" +
                    "<strong>Password:</strong> " + user.getPassword() + "<br><br>" +
                    "For security reasons, please consider changing your password after logging in.<br><br>" +
                    additionalMessage + "<br><br>" +
                    "Best regards,<br>" +
                    "Your System Team" +
                    "</p></div></body></html>";

            helper.setText(emailBody, true);

            // Send message
            mailSender.send(message);
            System.out.println("Email successfully sent to: " + user.getEmail());
            return true; // Return true if email is sent successfully
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false; // Return false if there's an error sending email
        }
    }

    public void sendResetPasswordEmail(EmailReceiverDto emailReceiver) throws Exception {
        if (userService.findByEmail(emailReceiver.getEmail()) == null)
            throw new EntityNotFoundException("User with " + emailReceiver.getEmail() + " does not exist!");

        String resetHash = UUID.randomUUID().toString();
        resetRequestCache.put(resetHash, emailReceiver.getEmail());

        JavaMailSender mailSender = getJavaMailSender();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        String subject = "You may reset your password.";

        String resetPasswordUrl = settingService.findValueByKey("app_host") + ":"
                + settingService.findValueByKey("app_port") + "/public/reset-password?hash=" + resetHash;
        String emailText = "Please click the link below to reset your password. <br> <a href=\"" + resetPasswordUrl
                + "\">Reset Password</a>";
        messageHelper.setSubject(subject);
        message.setText(emailText, "UTF-8", "html");
        messageHelper.setTo(emailReceiver.getEmail());
        mailSender.send(message);
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


