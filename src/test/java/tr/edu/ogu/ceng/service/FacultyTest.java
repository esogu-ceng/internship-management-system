package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

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
		facultyService = new FacultyService(facultyRepository);
	}

	@Test
	void is_faculty_added_successfully() {
		var facultyToSave = FacultyDto.builder().name("Faculty")
				.createDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.updateDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.build();

		when(facultyRepository.save(any(Faculty.class))).thenReturn(modelMapper.map(facultyToSave, Faculty.class));
		var actual = facultyService.addFaculty(facultyToSave);

		assertNotNull(actual);
		assertEquals(facultyToSave.getName(), actual.getName());
		assertEquals(facultyToSave.getCreateDate(), actual.getCreateDate());
		assertEquals(facultyToSave.getUpdateDate(), actual.getUpdateDate());

		verify(facultyRepository).save(modelMapper.map(facultyToSave, Faculty.class));
	}

}
