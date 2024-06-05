package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tr.edu.ogu.ceng.controller.StudentController;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.InternshipApplicationRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.InternshipApplicationDto;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.InternshipApplication;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.FacultySupervisorService;
import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.util.PageableUtil;

@ExtendWith(MockitoExtension.class)
public class InternshipApplicationTest {

    private StudentService studentService;
    private StudentRepository studentRepository;
    private CompanyRepository companyRepository;
    private InternshipApplicationRepository internshipApplicationRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        companyRepository = mock(CompanyRepository.class);
        internshipApplicationRepository = mock(InternshipApplicationRepository.class);
        modelMapper = new ModelMapper();
        studentService = new StudentService(studentRepository, null, null, companyRepository, internshipApplicationRepository, null, null, modelMapper, null, null);
    }

    @Test
    void testApplyForInternship() {
        // Mock data
        Long studentId = 1L;
        Long companyId = 2L;
        Student student = new Student();
        student.setId(studentId);
        Company company = new Company();
        company.setId(companyId);
        InternshipApplication savedApplication = new InternshipApplication();
        savedApplication.setId(1L);

        // Mock repository responses
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(internshipApplicationRepository.save(any())).thenReturn(savedApplication);

        // Call the method
        InternshipApplicationDto result = studentService.applyForInternship(studentId, companyId);

        // Verify interactions and assert result
        verify(studentRepository, times(1)).findById(studentId);
        verify(companyRepository, times(1)).findById(companyId);
        verify(internshipApplicationRepository, times(1)).save(any());
        assertEquals(savedApplication.getId(), result.getId());
    }

    @Test
    void testApplyForInternship_ThrowEntityNotFoundExceptionForStudent() {
        // Mock data
        Long studentId = 1L;
        Long companyId = 2L;

        // Mock repository responses
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Call the method and assert exception
        assertThrows(tr.edu.ogu.ceng.service.Exception.EntityNotFoundException.class, () -> {
            studentService.applyForInternship(studentId, companyId);
        });

        // Verify interactions
        verify(studentRepository, times(1)).findById(studentId);
        verifyNoMoreInteractions(studentRepository);
        verifyNoInteractions(companyRepository, internshipApplicationRepository);
    }

    @Test
    void testApplyForInternship_ThrowEntityNotFoundExceptionForCompany() {
        // Mock data
        Long studentId = 1L;
        Long companyId = 2L;
        Student student = new Student();
        student.setId(studentId);

        // Mock repository responses
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        // Call the method and assert exception
        assertThrows(tr.edu.ogu.ceng.service.Exception.EntityNotFoundException.class, () -> {
            studentService.applyForInternship(studentId, companyId);
        });

        // Verify interactions
        verify(studentRepository, times(1)).findById(studentId);
        verify(companyRepository, times(1)).findById(companyId);
        verifyNoMoreInteractions(studentRepository, companyRepository);
        verifyNoInteractions(internshipApplicationRepository);
    }

    @Test
    void testGetStudentApplications() {
        // Mock data
        Long studentId = 1L;
        InternshipApplication application1 = new InternshipApplication();
        application1.setId(1L);
        InternshipApplication application2 = new InternshipApplication();
        application2.setId(2L);
        List<InternshipApplication> applications = new ArrayList<>();
        applications.add(application1);
        applications.add(application2);

        // Mock repository responses
        when(internshipApplicationRepository.findByStudentId(studentId)).thenReturn(applications);

        // Call the method
        List<InternshipApplicationDto> result = studentService.getStudentApplications(studentId);

        // Verify interactions and assert result
        verify(internshipApplicationRepository, times(1)).findByStudentId(studentId);
        assertEquals(2, result.size());
        assertEquals(application1.getId(), result.get(0).getId());
        assertEquals(application2.getId(), result.get(1).getId());
    }

    @Test
    void testGetStudentApplications_EmptyList() {
        // Mock data
        Long studentId = 1L;

        // Mock repository responses
        when(internshipApplicationRepository.findByStudentId(studentId)).thenReturn(new ArrayList<>());

        // Call the method
        List<InternshipApplicationDto> result = studentService.getStudentApplications(studentId);

        // Verify interactions and assert result
        verify(internshipApplicationRepository, times(1)).findByStudentId(studentId);
        assertEquals(0, result.size());
    }
}