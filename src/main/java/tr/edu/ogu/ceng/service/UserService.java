package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.UserRequestDto;
import tr.edu.ogu.ceng.dto.responses.UserResponseDto;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.security.UserPrincipal;
import tr.edu.ogu.ceng.service.Exception.InvalidArgumentException;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	public Page<UserDto> getAllUsers(Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting all users with pageable: {}", pageable);
			Page<User> users = userRepository.findAll(pageable);

			if (users.isEmpty()) {
				log.warn("No users found.");
			}
			Page<UserDto> userDtos = users.map(user -> modelMapper.map(user, UserDto.class));
			return userDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting users: {}", e.getMessage());
			throw e;
		}
	}

	public UserDto saveUser(UserDto userDto) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			if (userDto.getId() != null && userRepository.existsById(userDto.getId())) {
				throw new IllegalArgumentException("User with ID " + userDto.getId() + " already exists.");
			}
			User user = modelMapper.map(userDto, User.class);
			LocalDateTime dateTime = LocalDateTime.now();
			user.setCreateDate(dateTime);
			user.setUpdateDate(dateTime);
			user.setActivity(true);
			User savedUser = userRepository.save(user);
			log.info("User saved successfully with id: {}", savedUser.getId());
			return modelMapper.map(savedUser, UserDto.class);
		} catch (Exception e) {
			log.error("An error occurred while saving user: {}", e.getMessage());
			throw e;
		}
	}

	public User saveUser(User user) {
		try {
			LocalDateTime dateTime = LocalDateTime.now();
			user.setCreateDate(dateTime);
			user.setUpdateDate(dateTime);
			user.setPassword(encodeUserPassword(user.getPassword()));
			user.setActivity(true);
			User savedUser = userRepository.save(user);
			log.info("User saved successfully with id: {}", savedUser.getId());
			return savedUser;
		} catch (Exception e) {
			log.error("An error occurred while saving user: {}", e.getMessage());
			throw e;
		}
	}

	public User findByEmail(String email) {
		try {
			log.info("Getting user by email: {}", email);
			User user = userRepository.findByEmail(email);
			if (user == null) {
				log.warn("No user found with email: {}", email);
			}
			return user;
		} catch (Exception e) {
			log.error("An error occurred while getting user by email: {}", e.getMessage());
			throw e;
		}
	}

	public boolean deleteUser(long id) {
		try {
			if (!userRepository.existsById(id)) {
				log.warn("User not found with id: {}", id);
				throw new EntityNotFoundException("User Not Found!");
			}
			userRepository.deleteById(id);
			log.info("User deleted successfully with id: {}", id);
			return true;
		} catch (Exception e) {
			log.error("An error occurred while deleting user with id: {}: {}", id, e.getMessage());
			return false;
		}
	}

	public User updateUser(User user) {
		if (!userRepository.existsById(user.getId())) {
			throw new EntityNotFoundException("User not found!");
		}
		return userRepository.save(user);
	}

	public String encodeUserPassword(String rawPass) {
		return passwordEncoder.encode(rawPass);
	}

	@Scheduled(fixedDelay = 3000000, initialDelay = 1)
	public void encodeAllSavedPasswords() {
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			if (user.getPassword().length() > 59 && user.getPassword().startsWith("$")) {
				continue;
			}
			user.setPassword(encodeUserPassword(user.getPassword()));
		}
		userRepository.saveAll(userList);
	}

	public UserResponseDto updateUser(UserRequestDto userDto) {
		try {
			if (!userRepository.existsById(userDto.getId())) {
				log.warn("There is no user with the entered ID.");
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			ModelMapper modelMapper = new ModelMapper();
			User user = modelMapper.map(userDto, User.class);
			LocalDateTime dateTime = LocalDateTime.now();
			user.setCreateDate(userRepository.getById(userDto.getId()).getCreateDate());
			user.setUpdateDate(dateTime);
			user = userRepository.save(user);
			log.info("User with ID {} has been updated", user.getId());
			return modelMapper.map(user, UserResponseDto.class);
		} catch (tr.edu.ogu.ceng.service.Exception.EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public boolean setUserActivity(Long id, boolean status) {
		try {
			if (!userRepository.existsById(id)) {
				log.warn("User not found with id: {}", id);
				throw new EntityNotFoundException("User Not Found!");
			}
			if (status != true && status != false) {
				log.warn("Status value not boolean type!");
				throw new InvalidArgumentException("Invalid status type for user activity!");
			}
			User user = userRepository.getById(id);
			user.setActivity(status);
			user = userRepository.save(user);
			log.info("User activity setted to {} successfully with id: {}", status, id);
			return true;
		} catch (Exception e) {
			log.error("An error occurred while setting activity of user with id: {}: {}", id, e.getMessage());
			return false;
		}
	}
	
	public UserDto getLoggedInUser() {
		ModelMapper modelMapper = new ModelMapper();
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDto loggedInUser = modelMapper.map(userPrincipal.getUser(), UserDto.class);
		return loggedInUser;
	}
}
