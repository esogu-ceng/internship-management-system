package tr.edu.ogu.ceng.service;


import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import tr.edu.ogu.ceng.dto.EmailReceiverDto;
import tr.edu.ogu.ceng.dto.ResetPasswordDto;
import tr.edu.ogu.ceng.dto.requests.UserRequestDto;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.InvalidTokenException;
import tr.edu.ogu.ceng.service.Exception.PasswordsNotMatchedException;

@Service
public class ForgotPasswordService {
	@Autowired
	private UserService userService;

	@Autowired
	private SettingService settingService;


	private static Cache<String, String> resetRequestCache = CacheBuilder.newBuilder().maximumSize(1000)
			.expireAfterWrite(5, TimeUnit.MINUTES).build();

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

	public void updatePassword(ResetPasswordDto resetPasswordDto) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		String hash = resetPasswordDto.getHash();
		if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword()))
			throw new PasswordsNotMatchedException();
		if (resetRequestCache.getIfPresent(hash) == null)
			throw new InvalidTokenException();

		String email = resetRequestCache.getIfPresent(hash);
		User user = userService.findByEmail(email);
		user.setPassword(userService.encodeUserPassword(resetPasswordDto.getPassword()));
		UserRequestDto userRequestDto = modelMapper.map(user, UserRequestDto.class);
		userService.updateUser(userRequestDto);
		resetRequestCache.invalidate(hash);
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
}
