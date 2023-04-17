package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.User;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        try {
            log.info("Getting all users with pageable: {}", pageable);
            Page<User> users = userRepository.findAll(pageable);
            if (users.isEmpty()) {
                log.warn("No users found.");
            }
            return users;
        } catch (Exception e) {
            log.error("An error occurred while getting users: {}", e.getMessage());
            throw e;
        }
    }

    public User saveUser(User user) {
        try {
            Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
            user.setCreateDate(localDateTime);
            user.setUpdateDate(localDateTime);
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
            throw e;
        }
    }
}
