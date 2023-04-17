package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
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

    public UserType saveUsertype (UserType userType){
    	Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
        userType.setCreateDate(localDateTime);
        userType.setUpdateDate(localDateTime);
        return userTypeRepository.save(userType);
    }
    
    public UserType getUserTypeId(UserTypeEnum userTypeEnum) {
        Optional<UserType> optionalUserType = userTypeRepository.findById(userTypeEnum.getId());
        UserType userType = optionalUserType.orElse(null);
        return userType;
    }
}
