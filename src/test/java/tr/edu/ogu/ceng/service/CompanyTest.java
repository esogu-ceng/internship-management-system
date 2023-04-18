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
		var companyToSave = CompanyDto.builder()
				.name("Test")
				.address("Test")
				.phone_number("Test")
				.fax_number("Test")
				.email("Test")
				.scope("Test")
				.createDate(new Timestamp(2023, 04, 17, 0, 0, 0, 0))
				.updateDate(new Timestamp(2023, 04, 17, 1, 0, 0, 0))
				.build();

		when(companyRepository.save(any(Company.class))).thenReturn(modelMapper.map(companyToSave, Company.class));

		var actual = companyService.addCompany(companyToSave);

		assertNotNull(actual);
		assertEquals(companyToSave.getName(), actual.getName());
		assertEquals(companyToSave.getAddress(), actual.getAddress());
		assertEquals(companyToSave.getPhone_number(), actual.getPhone_number());
		assertEquals(companyToSave.getFax_number(), actual.getFax_number());
		assertEquals(companyToSave.getEmail(), actual.getEmail());
		assertEquals(companyToSave.getScope(), actual.getScope());

		verify(companyRepository).save(modelMapper.map(companyToSave, Company.class));
	}

}
