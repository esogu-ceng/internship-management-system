package tr.edu.ogu.ceng.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

public class StudentTest {

	@Mock
	StudentRepository studentRepository;

	StudentService studentService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		studentService = new StudentService(studentRepository);
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
		var studentToSave = Student.builder().name("test").surname("test").id(6L).tckn("test").studentNo("test").grade("test").phoneNumber("test")
				.province("test").subprovince("test").zipCode("test").motherName("test").fatherName("test").birthPlace("test")
				.birthDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.idCardSerialNo("test").idRegisterProvince("test").idRegisterSubprovince("test").idRegisterStreetVillage("test").idRegisterVolumeNo("test")
				.idRegisterFamilySerialNo("test").idRegistryOffice("test").idRegistryReason("test").faculty(new Faculty()).build();

		when(studentRepository.save(any(Student.class))).thenReturn(studentToSave);

		final var actual = studentService.addStudent(new Student());

		assertThat(actual).usingRecursiveComparison().isEqualTo(studentToSave);
		verify(studentRepository).save(any(Student.class));
		verifyNoMoreInteractions(studentRepository);
	}

}
