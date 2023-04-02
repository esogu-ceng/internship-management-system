package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
    	Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
        user.setCreateDate(localDateTime);
        user.setUpdateDate(localDateTime);
        return userRepository.save(user);
    }
    
    public User findByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
}
