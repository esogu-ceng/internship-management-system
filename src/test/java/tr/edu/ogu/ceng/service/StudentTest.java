package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

public class StudentTest {

	@Mock
	StudentRepository studentRepository;
	@Mock
	StudentService studentService;

	@Mock
	UserRepository userRepository;
	@Mock
	UserService userService;

	@Mock
	SettingRepository settingRepository;
	@Mock
	FacultyService facultyService;
	@Mock
	FacultyRepository facultyRepository;
	@Mock
	FacultySupervisorService facultySupervisorService;

   @Mock
  EmailService emailService;
	@Mock
	ModelMapper modelMapper;



	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		studentService = new StudentService(studentRepository, userRepository, userService,settingRepository,facultyService
				, facultySupervisorService, new ModelMapper(),emailService);
	}

	@Test
	public void when_resultIsNotNull_then_returnValidDTO() {
		when(studentRepository.findById(6L)).thenThrow(new EntityNotFoundException());

		assertThrows(EntityNotFoundException.class, () -> {
			studentService.getStudent(6L);
		});

	}

	@Test
	void should_save_one_student() {

		// FIXME This test case will be extended to mock all used repository methods
		// like facultyRepository.get etc.
		LocalDateTime dateTime = LocalDateTime.now();
		var savedUser = new User(1002L, "passwordHash", "", null, dateTime, dateTime, null, false);
		var SavedStudent = new Student(6L, "test", "test", "test", "test", null, null, null,
				new Timestamp(2000, 01, 01, 0, 0, 0, 0), dateTime, dateTime, savedUser, new Faculty(), "address",null);

		var user = new User(1002L, "passwordHash", "", null, dateTime, dateTime, null, false);
		user.setId(null);
		var student = new Student(6L, "test", "test", "test", "test", null, null, null,
				new Timestamp(2000, 01, 01, 0, 0, 0, 0), dateTime, dateTime, savedUser, new Faculty(), "address",null);
		student.setId(null);

		when(userRepository.save(any(User.class))).thenReturn(savedUser);
		when(studentRepository.save(any(Student.class))).thenReturn(SavedStudent);

		var actual = studentService.addStudent(student);

		assertNotNull(actual);
		assertEquals(student.getName(), actual.getName());
		assertEquals(student.getSurname(), actual.getSurname());
		assertEquals(student.getStudentNo(), actual.getStudentNo());
		assertEquals(student.getGrade(), actual.getGrade());
		assertEquals(student.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(student.getBirthPlace(), actual.getBirthPlace());
		assertEquals(student.getBirthDate(), actual.getBirthDate());
		assertEquals(student.getAddress(), actual.getAddress());


	}





}
