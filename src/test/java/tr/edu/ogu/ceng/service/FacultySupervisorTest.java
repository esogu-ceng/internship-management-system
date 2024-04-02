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
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Language;
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

		var modelFaculty = new Faculty(1L, "Faculty", localDateTime, localDateTime);
		var modelUser = new User(3L, "password", "email", UserType.FACULTYSUPERVISOR, localDateTime,
				localDateTime, new Language(1L, "Türkçe", "tr", "tr", "TR", "Türkiye"), true);
		var modelFacultySupervisor = new FacultySupervisor(4L, "Name", "Surname", "Phone", "No", localDateTime,
				localDateTime, modelUser, modelFaculty);

		when(facultyRepository.save(any(Faculty.class))).thenReturn(modelFaculty);
		when(userService.saveUser(any(User.class))).thenReturn(modelUser);
		when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(modelFacultySupervisor);

		var facultySupervisor = new FacultySupervisor(4L, "Name", "Surname", "Phone", "No", localDateTime,
				localDateTime, modelUser, modelFaculty);

		var actual = facultySupervisorService.addFacultySupervisor(facultySupervisor);

		assertNotNull(actual);
		assertEquals(modelFacultySupervisor.getId(), actual.getId());
		assertEquals(modelFacultySupervisor.getName(), actual.getName());
		assertEquals(modelFacultySupervisor.getSurname(), actual.getSurname());
		assertEquals(modelFacultySupervisor.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(modelFacultySupervisor.getSupervisorNo(), actual.getSupervisorNo());
	}
}
