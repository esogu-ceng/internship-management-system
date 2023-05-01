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

		LocalDateTime localDateTime = LocalDateTime.now();

		var modelFaculty = Faculty.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime).build();
		when(facultyRepository.save(any(Faculty.class))).thenReturn(modelFaculty);
		var DtoFaculty = FacultyDto.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime).build();

		var actual = facultyService.addFaculty(DtoFaculty);

		assertNotNull(actual);
		assertEquals(modelFaculty.getId(), actual.getId());
		assertEquals(modelFaculty.getName(), actual.getName());
	}

}
