package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.UserTypeDto;
import tr.edu.ogu.ceng.enums.UserTypeEnum;
import tr.edu.ogu.ceng.model.UserType;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserTypeService {
	@Autowired
	private UserTypeRepository userTypeRepository;


	public UserTypeDto saveUsertype(UserTypeDto userTypeDto) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			UserType userType = modelMapper.map(userTypeDto, UserType.class);
			userType.setCreateDate(new Timestamp(System.currentTimeMillis()));
			userType.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			userType.setId((long) 0);
			UserType savedUserType = userTypeRepository.save(userType);
			log.info("UserType saved successfully with id: {}", savedUserType.getId());
			return modelMapper.map(savedUserType, UserTypeDto.class);
		} catch (Exception e) {
			log.error("An error occurred while saving userType: {}", e.getMessage());
			throw e;
		}
	}

    
    public UserType getUserTypeId(UserTypeEnum userTypeEnum) {
        Optional<UserType> optionalUserType = userTypeRepository.findById(userTypeEnum.getId());
        UserType userType = optionalUserType.orElse(null);
        return userType;
    }

}
