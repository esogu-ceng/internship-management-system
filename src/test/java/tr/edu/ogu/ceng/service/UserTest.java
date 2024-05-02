package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.DataAccessException;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

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
		var userToSave = new User(1002L, rawPassword, "Test@test.com", UserType.STUDENT, dateTime, dateTime,
				null, false);

		var savedUser = new User(1002L, passwordEncoder.encode(rawPassword), "Test@test.com", UserType.STUDENT,
				dateTime, dateTime, null, false);

		when(userRepository.existsById(any(Long.class))).thenReturn(false);
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		var actual = userService.addUser(userToSave);

		assertNotNull(actual);
		assertEquals(userToSave.getId(), actual.getId());
		assertTrue(passwordEncoder.matches(rawPassword, actual.getPassword()));
		assertEquals(userToSave.getEmail(), actual.getEmail());
		assertEquals(userToSave.getUserType(), actual.getUserType());

	}

	@Test
	void testDeleteUserSuccess() {
		long userId = 1L;

		// userRepository.existsById(userId) mocked to return true
		when(userRepository.existsById(userId)).thenReturn(true);

		// userRepository.deleteById(userId) mocked to return void
		doNothing().when(userRepository).deleteById(userId);

		boolean deleteUserResult = userService.deleteUser(userId);

		// Assert that deleteUser returned true
		assertTrue(deleteUserResult);

		// Verify that userRepository.deleteById was called with the correct userId
		verify(userRepository).deleteById(userId);
	}

	@Test
	void testDeleteUserNotFound() {
		long userId = 2L;

		// userRepository.existsById(userId) mocked to return false
		when(userRepository.existsById(userId)).thenReturn(false);

		boolean deleteUserResult = userService.deleteUser(userId);

		// Assert that deleteUser returned false
		assertFalse(deleteUserResult);

		// Verify that userRepository.deleteById was not called
		verify(userRepository, never()).deleteById(userId);
	}


	@Test
	void testDeleteUserUnexpectedException() {
		long userId = 4L;

		// userRepository.existsById(userId) mocked to return true
		when(userRepository.existsById(userId)).thenReturn(true);

		// userRepository.deleteById(userId) mocked to throw Exception
		doThrow(new RuntimeException("Beklenmedik hata")).when(userRepository).deleteById(userId);

		// Assert that an exception is thrown
		assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
	}

}



