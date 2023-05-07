package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.UserTypeDto;
import tr.edu.ogu.ceng.dto.requests.FacultyRequestDto;
import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.requests.UserRequestDto;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.model.UserType;

public class FacultySupervisorTest {

    @Mock
    FacultySupervisorRepository facultySupervisorRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserTypeRepository userTypeRepository;

    @Mock
    FacultyRepository facultyRepository;

    FacultySupervisorService facultySupervisorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        facultySupervisorService = new FacultySupervisorService(facultySupervisorRepository, userRepository,
                userTypeRepository, new ModelMapper());
    }

    @Test
    void is_faculty_supervisor_added_successfully() {

        LocalDateTime localDateTime = LocalDateTime.now();

        var modelFaculty = Faculty.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime).build();
        var modelUserType = UserType.builder().id(2L).type("UserType").createDate(localDateTime).updateDate(localDateTime).build();
        var modelUser = User.builder().id(3L).username("Username").password("password").email("email").userType(modelUserType)
                .createDate(localDateTime).updateDate(localDateTime).build();
        var modelFacultySupervisor = FacultySupervisor.builder().id(4L).name("Name").surname("Surname").phoneNumber("Phone")
                .supervisorNo("No").createDate(localDateTime).updateDate(localDateTime).user(modelUser).faculty(modelFaculty)
                .build();

        when(facultyRepository.save(any(Faculty.class))).thenReturn(modelFaculty);
        when(userTypeRepository.save(any(UserType.class))).thenReturn(modelUserType);
        when(userRepository.save(any(User.class))).thenReturn(modelUser);
        when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(modelFacultySupervisor);

        var DtoFaculty = FacultyRequestDto.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime).build();
        var DtoUser = UserRequestDto.builder().id(3L).username("Username").password("password").email("email")
                .createDate(localDateTime).updateDate(localDateTime).build();
        var DtoFacultySupervisor = FacultySupervisorRequestDto.builder().id(4L).name("Name").surname("Surname").phoneNumber("Phone")
                .supervisorNo("No").user(DtoUser).faculty(DtoFaculty).build();

        var actual = facultySupervisorService.addFacultySupervisor(DtoFacultySupervisor);

        assertNotNull(actual);
        assertEquals(modelFacultySupervisor.getId(), actual.getId());
        assertEquals(modelFacultySupervisor.getName(), actual.getName());
        assertEquals(modelFacultySupervisor.getSurname(), actual.getSurname());
        assertEquals(modelFacultySupervisor.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(modelFacultySupervisor.getSupervisorNo(), actual.getSupervisorNo());
    }
}