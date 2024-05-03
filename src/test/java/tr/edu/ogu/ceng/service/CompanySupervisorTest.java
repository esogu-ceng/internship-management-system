package tr.edu.ogu.ceng.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.CompanySupervisorRepository;
import tr.edu.ogu.ceng.dto.responses.CompanySupervisorResponseDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import tr.edu.ogu.ceng.service.Exception.UserAlreadyExistsException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CompanySupervisorTest {

    @Mock
    private CompanySupervisorRepository supervisorRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanySupervisorService supervisorService;

    @Test
    void testGetById() {
        // Mock data
        Long id = 1L;
        CompanySupervisor supervisor = new CompanySupervisor();
        supervisor.setId(id);
        supervisor.setName("Test Test");

        ModelMapper mapper = new ModelMapper();
        when(supervisorRepository.findById(id)).thenReturn(Optional.of(supervisor));

        UserService userService = mock(UserService.class);
        CompanyService companyService = mock(CompanyService.class);
        EmailService emailService = mock(EmailService.class);
        MessageResource messageResource = mock(MessageResource.class);

        CompanySupervisorService supervisorService = new CompanySupervisorService(supervisorRepository, companyRepository, mapper, userService, companyService, emailService, messageResource);

        CompanySupervisorResponseDto response = supervisorService.getById(id);

        assertEquals(id, response.getId());
        assertEquals("Test Test", response.getName());
    }

    @Test
    void testDelete() {
        // Mock data
        Long id = 1L;
        CompanySupervisor supervisor = new CompanySupervisor();
        User user = new User();
        user.setId(10L);
        supervisor.setId(id);
        supervisor.setUser(user);

        UserService userService = mock(UserService.class);
        CompanyService companyService = mock(CompanyService.class);
        EmailService emailService = mock(EmailService.class);
        MessageResource messageResource = mock(MessageResource.class);
        ModelMapper mapper = mock(ModelMapper.class);

        CompanySupervisorRepository repository = mock(CompanySupervisorRepository.class);
        when(repository.findById(id)).thenReturn(Optional.of(supervisor));

        CompanySupervisorService supervisorService = new CompanySupervisorService(repository, companyRepository, mapper, userService, companyService, emailService, messageResource);

        supervisorService.delete(id);

        verify(repository).deleteById(id);
    }

    @Test
    void testGetUsersCompany() {
        // Mock data
        Long userId = 1L;
        Long companyId = 10L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setPassword("password");

        Company company = new Company();
        company.setId(companyId);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        UserPrincipal principal = new UserPrincipal(user, authorities);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);

        SecurityContextHolder.setContext(securityContext);

        when(companyRepository.findCompanyByCompanyUserId(userId)).thenReturn(company);

        Company result = supervisorService.getUsersCompany();

        assertEquals(companyId, result.getId());
    }

    @Test
    public void testCheckIfCompanySupervisorExistsByUserId() {
        Long userId = 1L;
        when(supervisorRepository.existsByUserId(userId)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> supervisorService.checkIfCompanySupervisorExistsByUserId(userId));

        verify(supervisorRepository).existsByUserId(userId);
    }

    @Test
    public void testAdd() {
        //add fonksiyonu 2 attr baktigi icin
        User user = new User();
        Company company = new Company();
        CompanySupervisor supervisorToAdd = new CompanySupervisor(1L, "Test", "Test", "1234567890", LocalDateTime.now(), LocalDateTime.now(), company, user);
        when(supervisorRepository.save(supervisorToAdd)).thenReturn(supervisorToAdd);

        CompanySupervisor addedSupervisor = supervisorService.add(supervisorToAdd);

        assertNotNull(addedSupervisor);
        assertEquals(LocalDateTime.now().getDayOfYear(), addedSupervisor.getCreateDate().getDayOfYear());
        assertEquals(LocalDateTime.now().getDayOfYear(), addedSupervisor.getUpdateDate().getDayOfYear());
        verify(supervisorRepository, times(1)).save(supervisorToAdd);
    }
}