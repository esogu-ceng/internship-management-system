package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.User;

public class UserTest {
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
	void is_user_saved_successfully() {
		LocalDateTime dateTime = LocalDateTime.now();
		var userToSave = UserDto.builder().id(1002L).username("TEST").password("Test").email("Test@test.com")
				.userType(UserType.STUDENT).createDate(dateTime).updateDate(dateTime).build();

		var savedUser = User.builder().id(1002L).username("TEST").password("Test").email("Test@test.com")
				.userType(UserType.STUDENT).createDate(dateTime).updateDate(dateTime).build();

		when(userRepository.existsById(any(Long.class))).thenReturn(false);
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		var actual = userService.saveUser(userToSave);

		assertNotNull(actual);
		assertEquals(userToSave.getId(), actual.getId());
		assertEquals(userToSave.getUsername(), actual.getUsername());
		assertEquals(userToSave.getPassword(), actual.getPassword());
		assertEquals(userToSave.getEmail(), actual.getEmail());
		assertEquals(userToSave.getUserType(), actual.getUserType());
		assertEquals(userToSave.getCreateDate(), actual.getCreateDate());
		assertEquals(userToSave.getUpdateDate(), actual.getUpdateDate());

	}
}
