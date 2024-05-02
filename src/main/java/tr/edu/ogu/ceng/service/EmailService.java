package tr.edu.ogu.ceng.service;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {

	private SettingService settingService;

	public void sendEmail(String sendTo, String subject, String message) {
		try {
			JavaMailSender mailSender = getJavaMailSender();
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

			messageHelper.setTo(sendTo);
			messageHelper.setSubject(subject);
			mimeMessage.setText(message, "UTF-8", "HTML");
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