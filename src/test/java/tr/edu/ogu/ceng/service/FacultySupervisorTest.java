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

import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.User;

public class FacultySupervisorTest {

	@Mock
	FacultySupervisorRepository facultySupervisorRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	UserTypeRepository userTypeRepository;

	FacultySupervisorService facultySupervisorService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		facultySupervisorService = new FacultySupervisorService(facultySupervisorRepository, userRepository,
				userTypeRepository, new ModelMapper());
	}

	@Test
	void is_faculty_supervisor_added_successfully() {

		// FIXME This test case will be extended to mock all used repository methods
		// like facultyRepository.get etc.
		LocalDateTime dateTime = LocalDateTime.now();
		var savedUser = User.builder().email("test").password("passwordHash").id(1L).username("test").build();
		var savedFacultySupervisor = FacultySupervisor.builder().id(1L).name("Faculty Supervisor").surname("test")
				.phoneNumber("test").supervisorNo("test").createDate(dateTime).updateDate(dateTime).user(savedUser)
				.faculty(new Faculty()).build();

		when(userRepository.save(any(User.class))).thenReturn(savedUser);
		when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(savedFacultySupervisor);

		var userToSave = UserDto.builder().email("test").password("passwordHash").username("test").build();
		var facultySupervisortoSave = FacultySupervisorDto.builder().name("Faculty Supervisor").surname("test")
				.phoneNumber("test").supervisorNo("test").user(userToSave).build();
		var actual = facultySupervisorService.addFacultySupervisor(facultySupervisortoSave);

		assertNotNull(actual);
		assertEquals(savedFacultySupervisor.getName(), actual.getName());
		assertEquals(savedFacultySupervisor.getSurname(), actual.getSurname());
		assertEquals(savedFacultySupervisor.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(savedFacultySupervisor.getSupervisorNo(), actual.getSupervisorNo());
	}
}
