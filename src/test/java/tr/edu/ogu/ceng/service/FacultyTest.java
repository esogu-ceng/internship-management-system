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
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.model.Faculty;

public class FacultyTest {

	@Mock
	FacultyRepository facultyRepository;
	FacultyService facultyService;
	ModelMapper modelMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		modelMapper = new ModelMapper();
		facultyService = new FacultyService(facultyRepository);
	}

	@Test
	public void is_faculty_added_successfully() {
		LocalDateTime dateTime = LocalDateTime.now();
		var facultyToSave = FacultyDto.builder().id(1L).name("Faculty").createDate(dateTime).updateDate(dateTime).build();
		var savedFaculty = Faculty.builder().id(0L).name("Faculty")
				.createDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();

		when(facultyRepository.save(any(Faculty.class))).thenReturn(savedFaculty);
		var actual = facultyService.addFaculty(facultyToSave);

		assertNotNull(actual);
		assertEquals(facultyToSave.getName(), actual.getName());
	}

}
