package tr.edu.ogu.ceng.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.InternshipEvaluateFormRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dto.InternshipEvaluateFormDto;
import tr.edu.ogu.ceng.dto.requests.InternshipEvaluateFormRequestDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class InternshipEvaluateFormTest {

    @Mock
    InternshipEvaluateFormRepository internshipEvaluateFormRepository;

    @InjectMocks
    InternshipEvaluateFormService internshipEvaluateFormService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInternshipEvaluateFormById() {
        Long id = 1L;
        InternshipEvaluateForm expectedForm = new InternshipEvaluateForm();
        expectedForm.setId(id);

        ModelMapper modelMapper = new ModelMapper();
        when(internshipEvaluateFormRepository.findById(id)).thenReturn(Optional.of(expectedForm));
        InternshipRepository internshipRepository = mock(InternshipRepository.class);
        CompanyRepository companyRepository= mock(CompanyRepository.class);
        SettingRepository settingsRepository = mock(SettingRepository.class);
        MessageResource messageResource = mock(MessageResource.class);
        InternshipEvaluateFormService internshipEvaluateFormService = new InternshipEvaluateFormService(internshipEvaluateFormRepository,internshipRepository,companyRepository,settingsRepository, modelMapper, messageResource);

        InternshipEvaluateForm actualFormDto = internshipEvaluateFormService.getInternshipEvaluateFormById(id);

        assertNotNull(actualFormDto);
        verify(internshipEvaluateFormRepository, times(1)).findById(id);
    }


}