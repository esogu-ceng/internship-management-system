package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dto.requests.FacultyRequestDto;
import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.requests.UserRequestDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.User;

public class FacultySupervisorTest {

	@Mock
	FacultySupervisorRepository facultySupervisorRepository;

	@Mock
	UserService userService;

	@Mock
	FacultyRepository facultyRepository;

	FacultySupervisorService facultySupervisorService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		facultySupervisorService = new FacultySupervisorService(facultySupervisorRepository, userService,
				new ModelMapper(), new MessageResource(null));
	}

	@Test
	void is_faculty_supervisor_added_successfully() {

		LocalDateTime localDateTime = LocalDateTime.now();

		var modelFaculty = Faculty.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime)
				.build();
		var modelUser = User.builder().id(3L).username("Username").password("password").email("email")
				.userType(UserType.FACULTYSUPERVISOR).createDate(localDateTime).updateDate(localDateTime).build();
		var modelFacultySupervisor = FacultySupervisor.builder().id(4L).name("Name").surname("Surname")
				.phoneNumber("Phone").supervisorNo("No").createDate(localDateTime).updateDate(localDateTime)
				.user(modelUser).faculty(modelFaculty).build();

		when(facultyRepository.save(any(Faculty.class))).thenReturn(modelFaculty);
		when(userService.saveUser(any(User.class))).thenReturn(modelUser);
		when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(modelFacultySupervisor);

		var DtoFaculty = FacultyRequestDto.builder().id(1L).name("Faculty").build();
		var DtoUser = UserRequestDto.builder().id(3L).username("Username").password("password").email("email").build();
		var DtoFacultySupervisor = FacultySupervisorRequestDto.builder().id(4L).name("Name").surname("Surname")
				.phoneNumber("Phone").supervisorNo("No").user(DtoUser).faculty(DtoFaculty).build();

		var actual = facultySupervisorService.addFacultySupervisor(DtoFacultySupervisor);

		assertNotNull(actual);
		assertEquals(modelFacultySupervisor.getId(), actual.getId());
		assertEquals(modelFacultySupervisor.getName(), actual.getName());
		assertEquals(modelFacultySupervisor.getSurname(), actual.getSurname());
		assertEquals(modelFacultySupervisor.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(modelFacultySupervisor.getSupervisorNo(), actual.getSupervisorNo());
	}
}
