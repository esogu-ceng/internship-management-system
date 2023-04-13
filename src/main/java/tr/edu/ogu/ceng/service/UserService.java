package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Page<User> getAllUsers(Pageable pageable) {
		if (userRepository.findAll(pageable) == null)
			return null;
		return userRepository.findAll(pageable);
	}

	public User saveUser(User user) {
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		user.setCreateDate(localDateTime);
		user.setUpdateDate(localDateTime);
		return userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean deleteUser(long id) {

		if (!userRepository.existsById(id))
			throw new EntityNotFoundException("User Not Found!");

		userRepository.deleteById(id);
		return true;
	}

}
