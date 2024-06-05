package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tr.edu.ogu.ceng.controller.StudentController;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.InternshipApplicationDto;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.FacultySupervisorService;
import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.util.PageableUtil;

@ExtendWith(MockitoExtension.class)
public class InternshipApplicationTest {

    @Mock
    private StudentService studentService;

    @Mock
    private FacultySupervisorService facultySupervisorService;

    @InjectMocks
    private StudentController studentController;

    private Student student;
    private FacultySupervisor facultySupervisor;
    private List<InternshipApplicationDto> applications;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");
        student.setTckn("12345678901");
        student.setStudentNo("S12345");
        student.setGrade("A");
        student.setPhoneNumber("1234567890");
        student.setBirthPlace("City");
        student.setBirthDate(Timestamp.valueOf(LocalDateTime.now()));
        student.setCreateDate(LocalDateTime.now());
        student.setUpdateDate(LocalDateTime.now());
        student.setAddress("Address");

        User user = new User();
        user.setId(1L);
        student.setUser(user);

        facultySupervisor = new FacultySupervisor();
        facultySupervisor.setId(1L);
        facultySupervisor.setName("Jane");
        facultySupervisor.setSurname("Smith");
        facultySupervisor.setPhoneNumber("0987654321");
        facultySupervisor.setSupervisorNo("F12345");
        facultySupervisor.setCreateDate(LocalDateTime.now());
        facultySupervisor.setUpdateDate(LocalDateTime.now());

        applications = new ArrayList<>();
        InternshipApplicationDto application1 = new InternshipApplicationDto();
        application1.setId(1L);
        application1.setStudentId(student.getId());
        application1.setCompanyId(1L);
        InternshipApplicationDto application2 = new InternshipApplicationDto();
        application2.setId(2L);
        application2.setStudentId(student.getId());
        application2.setCompanyId(2L);
        applications.add(application1);
        applications.add(application2);
    }

    @Test
    void testApplyForInternship() {
        Long studentId = 1L;
        Long companyId = 1L;

        when(studentService.applyForInternship(studentId, companyId)).thenReturn(applications.get(0));

        InternshipApplicationDto applicationDto = studentController.applyForInternship(studentId, companyId);
        assertNotNull(applicationDto);
        assertEquals(studentId, applicationDto.getStudentId());
        assertEquals(companyId, applicationDto.getCompanyId());
    }

    @Test
    void testGetStudentApplications() {
        Long studentId = 1L;

        when(studentService.getStudentApplications(studentId)).thenReturn(applications);

        List<InternshipApplicationDto> resultApplications = studentController.getStudentApplications(studentId);
        assertNotNull(resultApplications);
        assertEquals(applications.size(), resultApplications.size());
    }
}
