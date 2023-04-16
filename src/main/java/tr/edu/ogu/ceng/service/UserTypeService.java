package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.model.UserType;

@Slf4j
@Service
public class UserTypeService {
	@Autowired
	private UserTypeRepository userTypeRepository;

	public UserType saveUsertype(UserType userType) {
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		userType.setCreateDate(localDateTime);
		userType.setUpdateDate(localDateTime);
		log.info("User type saved successfully.");
		return userTypeRepository.save(userType);
	}
}
