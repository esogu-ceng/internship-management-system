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

import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.dto.InternshipRegistryDto;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipRegistry;

public class InternshipRegistryTest {

	@Mock
	InternshipRegistryRepository internshipRegistryRepository;
	InternshipRegistryService internshipRegistryService;
	ModelMapper modelMapper;
	InternshipRegistryDto internshipRegistryDto;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		internshipRegistryService = new InternshipRegistryService(internshipRegistryRepository, modelMapper);
	}

	@Test
	public void should_save_one_internshipRegistry() {
		var internshipRegistryToSave = InternshipRegistryDto.builder()
				.id(1L)
				.filePath("C:/Users/root/test")
				.name("internshipRegistry1")
				.type("pdf")
				.date(new Timestamp(2023, 04, 12, 0, 0, 0, 0))
				.internship(new Internship())
				.build();

		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenReturn(modelMapper.map(internshipRegistryToSave, InternshipRegistry.class));

		var actualIR = internshipRegistryService.addInternshipRegistry(internshipRegistryToSave);

		assertNotNull(actualIR);
		assertEquals(internshipRegistryToSave.getId(), actualIR.getId());
		assertEquals(internshipRegistryToSave.getFilePath(), actualIR.getFilePath());
		assertEquals(internshipRegistryToSave.getName(), actualIR.getName());
		assertEquals(internshipRegistryToSave.getType(), actualIR.getType());
		assertEquals(internshipRegistryToSave.getDate(), actualIR.getDate());
		assertEquals(internshipRegistryToSave.getInternship(), actualIR.getInternship());

		verify(internshipRegistryRepository).save(modelMapper.map(internshipRegistryToSave, InternshipRegistry.class));
	}

	// TODO Not Null olarak belirlenmiş alanların ayrıca tek tek kontrolü
	// yapılabilir.
}
