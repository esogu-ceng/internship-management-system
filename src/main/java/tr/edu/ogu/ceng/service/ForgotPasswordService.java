package tr.edu.ogu.ceng.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dto.EmailReceiverDto;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

@Service
public class ForgotPasswordService {
	@Autowired
	private UserService userService;
	
	//FIXME:  @Autowired
	//FIXME:  private SettingService settingService;
	
	public void sendResetPasswordEmail(EmailReceiverDto emailReceiver) throws Exception{
		if(userService.findByEmail(emailReceiver.getEmail()) == null)
			throw new Exception("User with " + emailReceiver.getEmail() +" does not exist!");
		
		JavaMailSender mailSender = getJavaMailSender();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		
		String subject = "You may reset your password.";
		String emailText = "Please click the link below to reset your password."; // TODO: reset-password API
		
		messageHelper.setSubject(subject);
		messageHelper.setText(emailText);
		messageHelper.setTo(emailReceiver.getEmail());
		mailSender.send(message);
	}
	
	 private JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    //FIXME: mailSender.setHost(settingsService.getMailHost());
	    //FIXME: mailSender.setPort(settingsService.getMailPort());
	    //FIXME: mailSender.setUsername(settingsService.getMailUsername());
	    //FIXME: mailSender.setPassword(settingsService.getMailPassword());
	        
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	        
	    return mailSender;
	}
}
