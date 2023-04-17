package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

public class StudentTest {

	@Mock
	StudentRepository studentRepository;

	StudentService studentService;
	UserRepository userRepository;
	UserTypeService userTypeService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		studentService = new StudentService(studentRepository, userRepository, userTypeService);
	}

	@Test
	public void when_resultIsNotNull_then_returnValidDTO() {
		when(studentRepository.findById(6L)).thenThrow(new javax.persistence.EntityNotFoundException());

		assertThrows(EntityNotFoundException.class, () -> {
			studentService.getStudent(6L);
		});

	}

	@Test
	void should_save_one_student() {
		var studentToSave = Student.builder().name("test").surname("test").id(6L).tckn("test").studentNo("test")
				.grade("test").phoneNumber("test").province("test").subprovince("test").zipCode("test")
				.motherName("test").fatherName("test").birthPlace("test")
				.birthDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0)).idCardSerialNo("test").idRegisterProvince("test")
				.idRegisterSubprovince("test").idRegisterStreetVillage("test").idRegisterVolumeNo("test")
				.idRegisterFamilySerialNo("test").idRegistryOffice("test").idRegistryReason("test")
				.faculty(new Faculty()).build();

		when(studentRepository.save(any(Student.class))).thenReturn(studentToSave);

		var actual = studentService.addStudent(studentToSave);

		assertNotNull(actual);
		assertEquals(studentToSave.getName(), actual.getName());
		assertEquals(studentToSave.getSurname(), actual.getSurname());
		assertEquals(studentToSave.getId(), actual.getId());
		assertEquals(studentToSave.getStudentNo(), actual.getStudentNo());
		assertEquals(studentToSave.getGrade(), actual.getGrade());
		assertEquals(studentToSave.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(studentToSave.getProvince(), actual.getProvince());
		assertEquals(studentToSave.getSubprovince(), actual.getSubprovince());
		assertEquals(studentToSave.getZipCode(), actual.getZipCode());
		assertEquals(studentToSave.getMotherName(), actual.getMotherName());
		assertEquals(studentToSave.getFatherName(), actual.getFatherName());
		assertEquals(studentToSave.getBirthPlace(), actual.getBirthPlace());
		assertEquals(studentToSave.getBirthDate(), actual.getBirthDate());
		assertEquals(studentToSave.getIdCardSerialNo(), actual.getIdCardSerialNo());
		assertEquals(studentToSave.getIdRegisterProvince(), actual.getIdRegisterProvince());
		assertEquals(studentToSave.getIdRegisterSubprovince(), actual.getIdRegisterSubprovince());
		assertEquals(studentToSave.getIdRegisterStreetVillage(), actual.getIdRegisterStreetVillage());
		assertEquals(studentToSave.getIdRegisterVolumeNo(), actual.getIdRegisterVolumeNo());
		assertEquals(studentToSave.getIdRegisterFamilySerialNo(), actual.getIdRegisterFamilySerialNo());
		assertEquals(studentToSave.getIdRegistryOffice(), actual.getIdRegistryOffice());
		assertEquals(studentToSave.getIdRegistryReason(), actual.getIdRegistryReason());
		assertEquals(studentToSave.getFaculty(), actual.getFaculty());

		verify(studentRepository).save(studentToSave);
	}

}
