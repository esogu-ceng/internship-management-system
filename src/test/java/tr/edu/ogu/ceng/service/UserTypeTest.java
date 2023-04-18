package tr.edu.ogu.ceng.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.UserTypeDto;
import tr.edu.ogu.ceng.model.UserType;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTypeTest {

    @Mock
    UserTypeRepository userTypeRepository;
    UserTypeService userTypeService;
    

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        
        userTypeService = new UserTypeService(userTypeRepository);
    }
    

    @Test
    void is_user_type_saved_successfully() {
        var userTypeDtoToSave = UserTypeDto.builder()
                .id(1L)
                .name("Student")
                .build();

        var userTypeToSave = UserType.builder()
                .id(0L)
                .name("Student")
                .createDate(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .build();

        when(userTypeRepository.save(any(UserType.class))).thenReturn(userTypeToSave);

        var actual = userTypeService.saveUsertype(userTypeDtoToSave);

        assertEquals(userTypeToSave.getId(), actual.getId());
        assertEquals(userTypeToSave.getName(), actual.getName());
        assertEquals(userTypeToSave.getCreateDate(), actual.getCreateDate());
        assertEquals(userTypeToSave.getUpdateDate(), actual.getUpdateDate());

        verify(userTypeRepository).save(userTypeToSave);
    }
}
