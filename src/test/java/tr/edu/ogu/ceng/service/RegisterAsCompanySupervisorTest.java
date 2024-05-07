package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.requests.RegisterAsCompanySupervisorRequestDto;
import tr.edu.ogu.ceng.service.Exception.PasswordsNotMatchedException;
import tr.edu.ogu.ceng.service.Exception.UnconfirmedMailAddressException;

public class RegisterAsCompanySupervisorTest {

    @Mock
    private RegisterAsCompanySupervisorService registerService;

	@Mock
	CompanySupervisorService companySupervisorService;
	
	@Mock
	CompanyService companyService;
	
	@Mock
	UserService UserService;
	
	ModelMapper modelMapper;
	
	@Mock
	CompanyRepository companyRepository;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		ModelMapper modelMapper = new ModelMapper();
		registerService = new RegisterAsCompanySupervisorService(companySupervisorService, companyService, UserService,  modelMapper);
	}
 
    @Test
    public void testCheckIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress_ShouldThrowUnconfirmedMailAddressException() {
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setEmail("company2@ogu.edu.tr");
        // Şirketin e-posta adresi "example@example.com" olduğu için e-posta adresleri aynı etki alanına sahip değil
        request.setCompany(CompanyDto.builder()
                .id(1L)
                .name("Kuzey Ticaret")
                .email("kuzeyticaret@mail.com")
                .build());

        assertThrows(UnconfirmedMailAddressException.class, () -> {
            registerService.checkIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress(request);
        });
    }

    @Test
    public void testCheckIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress_ShouldNotThrowException() {
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setEmail("company2@ogu.edu.tr");
        // Şirketin e-posta adresi "example@example.com.tr" olduğu için e-posta adresleri aynı etki alanına sahip
        request.setCompany(CompanyDto.builder()
                .id(1L)
                .name("Kuzey Ticaret")
                .email("kuzeyticaret@mail.com")
                .build());

        // E-posta adresleri aynı etki alanına sahip olduğu için bir hata fırlatılmamalı
        assertDoesNotThrow(() -> {
            registerService.checkIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress(request);
        });
    }

    @Test
    public void testCheckIfPasswordsMatchingValidation_ShouldThrowPasswordsNotMatchedException() {
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setPassword("password");
        request.setConfirmPassword("notmatchingpassword");

        assertThrows(PasswordsNotMatchedException.class, () -> {
            registerService.checkIfPasswordsMatchingValidation(request);
        });
    }

    @Test
    public void testCheckIfPasswordsMatchingValidation_ShouldNotThrowException() {
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setPassword("password");
        request.setConfirmPassword("password");

        // Şifreler eşleştiği için bir hata fırlatılmamalı
        registerService.checkIfPasswordsMatchingValidation(request);
    }
}