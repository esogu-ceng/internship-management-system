package tr.edu.ogu.ceng.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import tr.edu.ogu.ceng.dto.UserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.UserDto;
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
		userService = new UserService(userRepository, new BCryptPasswordEncoder());
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
	public void get_user_by_id_returns_correct_user() {
		Long userId = 1L;
		String userEmail = "test@example.com";
		UserType userType = UserType.STUDENT;
		LocalDateTime createDate = LocalDateTime.of(2024, 4, 30, 12, 0);
		User user = new User(userId, "password", userEmail, userType, createDate, createDate, null, false);

		when(userRepository.getById(userId)).thenReturn(user);

		User resultUser = userService.GetUserById(userId);

		assertEquals(userId, resultUser.getId());
		assertEquals(userEmail, resultUser.getEmail());
		assertEquals(userType, resultUser.getUserType());
	}

	@Test
	public void find_user_by_email_returns_user_when_found() {
		String email = "test@example.com";
		User user = new User();
		user.setId(1L);
		user.setEmail(email);

		when(userRepository.findByEmail(email)).thenReturn(user);

		User foundUser = userService.findByEmail(email);

		assertNotNull(foundUser);
		assertEquals(email, foundUser.getEmail());
		assertEquals(user.getId(), foundUser.getId());

		verify(userRepository).findByEmail(email);
	}

	@Test
	public void find_user_by_email_returns_null_when_not_found() {
		String email = "nonexistent@example.com";

		when(userRepository.findByEmail(email)).thenReturn(null);

		User foundUser = userService.findByEmail(email);

		assertNull(foundUser);
		verify(userRepository).findByEmail(email);
	}

	@Test
	public void find_user_by_email_throws_exception_when_error_occurs() {
		String email = "test@example.com";

		when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Something went wrong"));

		assertThrows(RuntimeException.class, () -> {
			userService.findByEmail(email);
		});

		verify(userRepository).findByEmail(email);
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
	@Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "password", "email", UserType.STUDENT, LocalDateTime.now(), LocalDateTime.now(), null, false));
        users.add(new User(2L, "password", "email", UserType.STUDENT, LocalDateTime.now(), LocalDateTime.now(), null, false));
        users.add(new User(3L, "password", "email", UserType.STUDENT, LocalDateTime.now(), LocalDateTime.now(), null, false));

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<UserDto> result = userService.getAllUsers(pageable);

        assertEquals(users.size(), result.getContent().size());
        assertEquals(users.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(users.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(users.get(2).getId(), result.getContent().get(2).getId());
    }

	}
