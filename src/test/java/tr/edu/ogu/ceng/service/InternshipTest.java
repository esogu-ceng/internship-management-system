package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.model.Internship;

public class InternshipTest {
	@Mock
	InternshipRepository internshipRepository;
	InternshipService internshipService;
	ModelMapper modelMapper;
	InternshipDto internshipDto;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		modelMapper = new ModelMapper();
		internshipService = new InternshipService(internshipRepository, modelMapper);
	}

	@Test
	public void is_internship_added_successfully() {
		var internshipToSave = InternshipDto.builder().id(1004L).status("Test")
				.startDate(new Timestamp(2023, 04, 14, 0, 0, 0, 0)).endDate(new Timestamp(2023, 04, 30, 0, 0, 0, 0))
				.days(20).companyId(5L).studentId(8L).facultySupervisorId(2L).build();

		when(internshipRepository.save(any(Internship.class)))
				.thenReturn(modelMapper.map(internshipToSave, Internship.class));

		var actual = internshipService.addInternship(internshipToSave);

		assertNotNull(actual);
		assertEquals(internshipToSave.getId(), actual.getId());
		assertEquals(internshipToSave.getStatus(), actual.getStatus());
		assertEquals(internshipToSave.getStartDate(), actual.getStartDate());
		assertEquals(internshipToSave.getEndDate(), actual.getEndDate());
		assertEquals(internshipToSave.getDays(), actual.getDays());
		assertEquals(internshipToSave.getCompanyId(), actual.getCompanyId());
		assertEquals(internshipToSave.getStudentId(), actual.getStudentId());
		assertEquals(internshipToSave.getFacultySupervisorId(), actual.getFacultySupervisorId());
	}
}
