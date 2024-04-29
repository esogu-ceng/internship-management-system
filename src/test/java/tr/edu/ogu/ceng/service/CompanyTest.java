package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.util.PageableUtil;

class CompanyTest {

	@Mock
	CompanyRepository companyRepository;

    @Mock
    CompanyService companyService;

    MessageResource messageResource;
	ModelMapper modelMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		companyService = new CompanyService(companyRepository, messageResource);
	}

	@Test
	void should_save_one_company() {
		LocalDateTime dateTime = LocalDateTime.now();

		var companyToSave = CompanyDto.builder().id(1L).name("Test").address("Test").phoneNumber("Test")
				.faxNumber("Test").email("Test@test.com").scope("Test").description("Test").createDate(dateTime)
				.updateDate(dateTime).build();

		var savedCompany = new Company(1L, "Test", "Test", "Test", "Test", "Test", "Test", "Test", dateTime, dateTime);

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

    @Test
    void should_throw_exception_when_saving_company_fails() {
        LocalDateTime dateTime = LocalDateTime.now();

        var companyToSave = CompanyDto.builder().id(1L).name("Test").address("Test").phoneNumber("Test")
                .faxNumber("Test").email("Test@test.com").scope("Test").description("Test").createDate(dateTime)
                .updateDate(dateTime).build();

        when(companyRepository.save(any(Company.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> companyService.addCompany(companyToSave));
    }

    @Test
    void should_return_count_of_companies() {
        when(companyRepository.count()).thenReturn(1L);
        assertEquals(1, companyService.countCompanies());
    }

    @Test
    void should_return_all_companies() {
        LocalDateTime dateTime = LocalDateTime.now();

        var company1 = new Company(1L, "Test1", "Test1", "Test1", "Test1", "Test1", "Test1", "Test1", dateTime,
                dateTime);
        var company2 = new Company(2L, "Test2", "Test2", "Test2", "Test2", "Test2", "Test2", "Test2", dateTime,
                dateTime);

        Pageable pageable = PageableUtil.createPageRequest(0, 10, "name");
        var companiesPage = new PageImpl<>(List.of(company1, company2));

        when(companyRepository.findAll(any(Pageable.class))).thenReturn(companiesPage);

        var companies = companyService.getAllCompanies(pageable);

        assertNotNull(companies);
        assertEquals(2, companies.getSize());
    }

    @Test
    void should_delete_company_when_it_exists() {
        long id = 1L;
        when(companyRepository.existsById(id)).thenReturn(true);

        boolean result = companyService.deleteCompany(id);

        assertTrue(result);
        verify(companyRepository).deleteById(id);
    }

    @Test
    void should_not_delete_company_when_it_does_not_exist() {
        long id = 1L;
        when(companyRepository.existsById(id)).thenReturn(false);

        boolean result = companyService.deleteCompany(id);

        assertTrue(result);
        verify(companyRepository, never()).deleteById(id);
    }
}
