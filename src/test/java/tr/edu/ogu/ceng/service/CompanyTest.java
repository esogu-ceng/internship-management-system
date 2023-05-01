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

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.model.Company;

class CompanyTest {

	@Mock
	CompanyRepository companyRepository;
	CompanyService companyService;
	ModelMapper modelMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		companyService = new CompanyService(companyRepository);
	}

	@Test
	void should_save_one_company() {
		LocalDateTime dateTime = LocalDateTime.now();

		var companyToSave = CompanyDto.builder()
				.id(1L)
				.name("Test")
				.address("Test")
				.phoneNumber("Test")
				.faxNumber("Test")
				.email("Test@test.com")
				.scope("Test")
				.description("Test")
				.createDate(dateTime)
				.updateDate(dateTime)
				.build();

		var savedCompany = Company.builder()
				.id(1L)
				.name("Test")
				.address("Test")
				.phoneNumber("Test")
				.faxNumber("Test")
				.email("Test")
				.scope("Test")
				.description("Test")
				.createDate(dateTime)
				.updateDate(dateTime)
				.build();

		when(companyRepository.save(any(Company.class))).thenReturn(savedCompany);

		var actual = companyService.addCompany(companyToSave);

		assertNotNull(actual);
		assertEquals(companyToSave.getName(), actual.getName());
		assertEquals(companyToSave.getAddress(), actual.getAddress());
		assertEquals(companyToSave.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(companyToSave.getFaxNumber(), actual.getFaxNumber());
		assertEquals(companyToSave.getEmail(), actual.getEmail());
		assertEquals(companyToSave.getScope(), actual.getScope());
		assertEquals(companyToSave.getDescription(), actual.getDescription());

	}
}
