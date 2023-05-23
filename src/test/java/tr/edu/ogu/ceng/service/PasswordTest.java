package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tr.edu.ogu.ceng.dao.UserRepository;

public class PasswordTest {
	@Mock
	UserRepository userRepository;

	ModelMapper modelMapper;

	UserService userService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		modelMapper = new ModelMapper();
		userService = new UserService(userRepository, new BCryptPasswordEncoder());
	}

	@Test
	void is_password_generated_as_encoded() {
		String rawPass = "test";
		String encodedActualPass = userService.encodeUserPassword(rawPass);
		assertTrue(new BCryptPasswordEncoder().matches(rawPass, encodedActualPass));
	}
}
