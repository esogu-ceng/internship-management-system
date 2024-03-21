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




	private static Cache<String, String> resetRequestCache = CacheBuilder.newBuilder().maximumSize(1000)
			.expireAfterWrite(5, TimeUnit.MINUTES).build();


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


}
