package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.model.User;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
	@Autowired
	private UserRepository userRepository;

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
			User user = modelMapper.map(userDto, User.class);
			user.setCreateDate(new Timestamp(System.currentTimeMillis()));
			user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			user.setId((long) 0);
			User savedUser = userRepository.save(user);
			log.info("User saved successfully with id: {}", savedUser.getId());
			return modelMapper.map(savedUser, UserDto.class);
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
            throw e;
        }
    }
}
