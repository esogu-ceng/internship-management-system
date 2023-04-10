package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		if (userRepository.findAll() == null)
			return null;
		return userRepository.findAll();
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
