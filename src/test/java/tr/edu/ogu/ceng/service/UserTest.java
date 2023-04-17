package tr.edu.ogu.ceng.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.*;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserTest {
    @Mock
    UserRepository userRepository;
    UserService userService;
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void is_user_saved_successfully() {
        var userToSave = User.builder()
                .id(1002L)
                .username("TEST")
                .password("Test")
                .email("Test@test.com")
                .userType(new UserType())
                .createDate(new Timestamp(2023,04,17,0,0,0,0))
                .updateDate(new Timestamp(2023,04,17,1,0,0,0))
                .build();

        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        var actual = userService.saveUser(userToSave);

        assertNotNull(actual);
        assertEquals(userToSave.getId(), actual.getId());
        assertEquals(userToSave.getUsername(), actual.getUsername());
        assertEquals(userToSave.getPassword(), actual.getPassword());
        assertEquals(userToSave.getEmail(), actual.getEmail());
        assertEquals(userToSave.getUserType(), actual.getUserType());
        assertEquals(userToSave.getCreateDate(), actual.getCreateDate());
        assertEquals(userToSave.getUpdateDate(), actual.getUpdateDate());
        verify(userRepository).save(userToSave);
    }
}
