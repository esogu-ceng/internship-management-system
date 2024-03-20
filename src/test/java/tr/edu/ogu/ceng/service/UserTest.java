package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.User;

public class UserTest {
	@Mock
	UserRepository userRepository;

	ModelMapper modelMapper;

	UserService userService;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		modelMapper = new ModelMapper();
		userService = new UserService(userRepository, passwordEncoder);
		userService = new UserService(userRepository, passwordEncoder);
	}

	@Test
	void is_user_saved_successfully() {
		String rawPassword = "Test";
		LocalDateTime dateTime = LocalDateTime.now();
		var userToSave = new User(1002L, "TEST", rawPassword, "Test@test.com", UserType.STUDENT, dateTime, dateTime,
				null, false);

		var savedUser = new User(1002L, "TEST", passwordEncoder.encode(rawPassword), "Test@test.com", UserType.STUDENT,
				dateTime, dateTime, null, false);

		when(userRepository.existsById(any(Long.class))).thenReturn(false);
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		var actual = userService.saveUser(userToSave);

		assertNotNull(actual);
		assertEquals(userToSave.getId(), actual.getId());
		assertEquals(userToSave.getUsername(), actual.getUsername());
		assertTrue(passwordEncoder.matches(rawPassword, actual.getPassword()));
		assertEquals(userToSave.getEmail(), actual.getEmail());
		assertEquals(userToSave.getUserType(), actual.getUserType());

	}
}
