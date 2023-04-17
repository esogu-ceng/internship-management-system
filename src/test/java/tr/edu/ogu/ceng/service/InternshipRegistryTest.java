package tr.edu.ogu.ceng.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipRegistry;

public class InternshipRegistryTest {

	@Mock
	InternshipRegistryRepository internshipRegistryRepository;
	InternshipRegistryService internshipRegistryService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		internshipRegistryService = new InternshipRegistryService(internshipRegistryRepository);
	}

	@Test
	public void should_save_one_internshipRegistry() {
		var internshipRegistryToSave = InternshipRegistry.builder().id(1L).filePath("C:/Users/root/test")
				.name("internshipRegistry1").type("pdf").date(new Timestamp(2023, 04, 12, 0, 0, 0, 0))
				.createDate(new Timestamp(2023, 04, 17, 0, 0, 0, 0)).internship(new Internship()).build();

		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenReturn(internshipRegistryToSave);

		var actualIR = internshipRegistryService.addInternshipRegistry(internshipRegistryToSave);

		assertNotNull(actualIR);
		assertEquals(internshipRegistryToSave.getId(), actualIR.getId());
		assertEquals(internshipRegistryToSave.getFilePath(), actualIR.getFilePath());
		assertEquals(internshipRegistryToSave.getName(), actualIR.getName());
		assertEquals(internshipRegistryToSave.getType(), actualIR.getType());
		assertEquals(internshipRegistryToSave.getDate(), actualIR.getDate());
		assertEquals(internshipRegistryToSave.getCreateDate(), actualIR.getCreateDate());
		assertEquals(internshipRegistryToSave.getInternship(), actualIR.getInternship());

		verify(internshipRegistryRepository).save(internshipRegistryToSave);
	}

	// TODO Not Null olarak belirlenmiş alanların ayrıca tek tek kontrolü
	// yapılabilir.
}
