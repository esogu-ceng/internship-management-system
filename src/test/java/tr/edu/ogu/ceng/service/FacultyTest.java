package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.requests.FacultyRequestDto;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

public class FacultyTest {

	@Mock
	FacultyRepository facultyRepository;
	FacultyService facultyService;
	ModelMapper modelMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		modelMapper = new ModelMapper();
		facultyService = new FacultyService(facultyRepository, modelMapper);
	}

	@Test
	public void is_faculty_added_successfully() {

		LocalDateTime localDateTime = LocalDateTime.now();

		var modelFaculty = new Faculty(1L, "Faculty", localDateTime, localDateTime);
		when(facultyRepository.save(any(Faculty.class))).thenReturn(modelFaculty);
		var DtoFaculty = FacultyDto.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime)
				.build();

		var actual = facultyService.addFaculty(DtoFaculty);

		assertNotNull(actual);
		assertEquals(modelFaculty.getId(), actual.getId());
		assertEquals(modelFaculty.getName(), actual.getName());
	}
	
	@Test
	public void is_faculty_updated_successfully() {
	    LocalDateTime now = LocalDateTime.now();
	    Long facultyId = 1L;
	    FacultyRequestDto facultyRequestDto = FacultyRequestDto.builder()
	            .id(facultyId)
	            .name("New Faculty Name")
	            .build();
	    Faculty faculty = new Faculty(facultyId, "Old Faculty Name", now.minusDays(1), now.minusDays(1));

	    FacultyRepository facultyRepository = Mockito.mock(FacultyRepository.class);
	    when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(faculty));
	    when(facultyRepository.getById(facultyId)).thenReturn(faculty);
	    when(facultyRepository.save(any(Faculty.class))).thenAnswer(invocation -> invocation.getArgument(0));
	    ModelMapper modelMapper = new ModelMapper();
	    FacultyService facultyService = new FacultyService(facultyRepository, modelMapper);

	    FacultyDto updatedFacultyDto = facultyService.updateFaculty(facultyRequestDto);
	    Faculty updatedFaculty = modelMapper.map(updatedFacultyDto, Faculty.class);
	    
	    assertNotNull(updatedFacultyDto);
	    assertEquals(facultyId, updatedFacultyDto.getId());
	    assertEquals(facultyRequestDto.getName(), updatedFacultyDto.getName());
	    assertEquals(faculty.getCreateDate(), updatedFacultyDto.getCreateDate());
	    assertTrue(updatedFacultyDto.getUpdateDate().isAfter(updatedFacultyDto.getCreateDate()));
	    verify(facultyRepository).getById(facultyId);
	    verify(facultyRepository).save(updatedFaculty);
	}

	@Test
	public void updateFaculty_with_null_id_should_throw_exception() {
	    FacultyRequestDto requestDto = FacultyRequestDto.builder()
                .name("New Faculty Name")
	            .build();

	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                facultyService.updateFaculty(requestDto);
	    });
	    assertEquals("Faculty Id cannot be null", exception.getMessage());
	}
	    
	@Test
	public void updateFaculty_with_nonexistent_id_should_throw_exception() {
	    Long nonExistentId = 999L;
	    FacultyRequestDto requestDto = FacultyRequestDto.builder()
	            .id(nonExistentId)
	            .name("New Faculty Name")
	            .build();

	    when(facultyRepository.findById(nonExistentId)).thenReturn(Optional.empty());

	    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
	    	facultyService.updateFaculty(requestDto);
	    });
	    assertEquals("Faculty not found!", exception.getMessage());
	}
	
	@Test
    public void testGetFaculties() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Faculty faculty1 = new Faculty(1L, "Faculty1", localDateTime, localDateTime);
        Faculty faculty2 = new Faculty(2L, "Faculty2", localDateTime, localDateTime);

        Page<Faculty> page = new PageImpl<>(Arrays.asList(faculty1, faculty2));
        when(facultyRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<FacultyDto> result = facultyService.getFaculties(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Faculty1", result.getContent().get(0).getName());
        assertEquals("Faculty2", result.getContent().get(1).getName());
    }

    @Test
    public void testCountFaculties() {
        when(facultyRepository.count()).thenReturn(10L);
        Long count = facultyService.countFaculties();
        assertNotNull(count);
        assertEquals(10L, count);
    }
	  

}
