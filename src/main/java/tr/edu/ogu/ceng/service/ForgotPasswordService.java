package tr.edu.ogu.ceng.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dto.EmailReceiverDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class ForgotPasswordService {
	
	@Value("${spring.mail.username}")
	private String emailFrom;
	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender mailSender;
	
	
	public void sendResetPasswordEmail(EmailReceiverDto emailReceiver) throws MessagingException, Exception {
		if(userService.findByEmail(emailReceiver.getEmail()) == null)
			throw new Exception("User with " + emailReceiver.getEmail() +" does not exist!");
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		
		String subject = "You may reset your password.";
		String emailText = "Please click the link below to reset your password."; // TODO: reset-password API
		
		messageHelper.setSubject(subject);
		messageHelper.setText(emailText);
		messageHelper.setFrom(emailFrom);
		messageHelper.setTo(emailReceiver.getEmail());
		
		mailSender.send(message);
	}
}
