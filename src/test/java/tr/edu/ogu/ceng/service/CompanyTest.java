package tr.edu.ogu.ceng.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;

class CompanyTest {

	@Mock
	CompanyRepository companyRepository;
	CompanyService companyService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		companyService = new CompanyService(companyRepository);
	}

	@Test
	void should_save_one_company() {
		var companyToSave = Company.builder().name("test").address("test").phoneNumber("test").faxNumber("test").email("test").scope("test").build();

		when(companyRepository.save(any(Company.class))).thenReturn(companyToSave);

		var actual = companyService.addCompany(new Company());

		assertThat(actual).usingRecursiveComparison().isEqualTo(companyToSave);
		verify(companyRepository).save(any(Company.class));
		verifyNoMoreInteractions(companyRepository);

	}

}
