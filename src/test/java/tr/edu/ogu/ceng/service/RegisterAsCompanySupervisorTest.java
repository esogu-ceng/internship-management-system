import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidationTest {

    @Test
    public void testCheckIfPasswordsMatchingValidation() {
        
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setPassword("123456");
        request.setConfirmPassword("123456");

       
        PasswordValidation passwordValidation = new PasswordValidation();
        passwordValidation.checkIfPasswordsMatchingValidation(request);

       
        assertFalse(passwordValidation.hasError());
    }

    @Test
    public void testCheckIfPasswordsNotMatchingValidation() {
        
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setPassword("password1");
        request.setConfirmPassword("password2");

        
        PasswordValidation passwordValidation = new PasswordValidation();
        passwordValidation.checkIfPasswordsMatchingValidation(request);

        
        assertTrue(passwordValidation.hasError());
    }
}

public class EmailAddressValidationTest {

    @Test
    public void testCheckIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress() {
        
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setEmail("supervisor@example.com");

        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setEmail("company@example.com");

        CompanyService companyService = Mockito.mock(CompanyService.class);
        Mockito.when(companyService.getCompany(1L)).thenReturn(companyDto);

        EmailAddressValidation emailAddressValidation = new EmailAddressValidation(companyService);
        emailAddressValidation.checkIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress(request);

       
        assertFalse(emailAddressValidation.hasError());
    }

    @Test
    public void testCheckIfSupervisorEmailAddressNotMatchingConfirmedCompanyEmailAddress() {
        
        RegisterAsCompanySupervisorRequestDto request = new RegisterAsCompanySupervisorRequestDto();
        request.setEmail("supervisor@example.com");

        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setEmail("company2@example.com");

        
        CompanyService companyService = Mockito.mock(CompanyService.class);
        Mockito.when(companyService.getCompany(1L)).thenReturn(companyDto);

        
        EmailAddressValidation emailAddressValidation = new EmailAddressValidation(companyService);
        emailAddressValidation.checkIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress(request);

       
        assertTrue(emailAddressValidation.hasError());
    }
}
