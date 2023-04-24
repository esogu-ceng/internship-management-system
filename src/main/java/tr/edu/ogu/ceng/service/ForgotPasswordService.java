package tr.edu.ogu.ceng.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dto.EmailReceiverDto;
import tr.edu.ogu.ceng.dto.ResetPasswordDto;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.InvalidTokenException;
import tr.edu.ogu.ceng.service.Exception.PasswordsNotMatchedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

@Service
public class ForgotPasswordService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SettingService settingService;
	
	private User user;
	
	private Map<String, EmailReceiverDto> resetRequests = new HashMap<>();	
	
	public void sendResetPasswordEmail(EmailReceiverDto emailReceiver) throws Exception{
		if(userService.findByEmail(emailReceiver.getEmail()) == null)
			throw new EntityNotFoundException("User with " + emailReceiver.getEmail() +" does not exist!");
		
		user = userService.findByEmail(emailReceiver.getEmail());
		String resetHash = UUID.randomUUID().toString();
	    resetRequests.put(resetHash, emailReceiver);
		
		JavaMailSender mailSender = getJavaMailSender();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
		
		String subject = "You may reset your password.";
		String resetPasswordUrl = settingService.findValueByKey("app_host") + ":" + settingService.findValueByKey("app_port") + "/reset-password?hash="+ resetHash;
		String emailText = "Please click the link below to reset your password. <br> <a href=\"" + resetPasswordUrl + "\">Reset Password</a>";
		
		messageHelper.setSubject(subject);
		message.setText(emailText, "UTF-8", "html");
		messageHelper.setTo(emailReceiver.getEmail());
		mailSender.send(message);
	}
	
	public void updatePassword(ResetPasswordDto resetPasswordDto) throws Exception {
		String hash = resetPasswordDto.getHash();
		if(!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) 
			throw new PasswordsNotMatchedException();
		if(!resetRequests.containsKey(hash))
			throw new InvalidTokenException();
		
		user.setPassword(resetPasswordDto.getPassword());
		userService.updateUser(user);
		resetRequests.remove(hash);
	}
	
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
	 
	@Scheduled(fixedDelay = 300000)
	private void clearResetRequests() {
		resetRequests.clear();
	}
}
