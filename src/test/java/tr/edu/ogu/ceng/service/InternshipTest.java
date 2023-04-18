package tr.edu.ogu.ceng.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.Student;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InternshipTest {
    @Mock
    InternshipRepository internshipRepository;
    InternshipService internshipService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        internshipService = new InternshipService(internshipRepository);
    }

    @Test
    void is_internship_added_successfully() {
        var internshipToSave = Internship.builder()
                .id(1004L)
                .status("Test")
                .startDate(new Timestamp(2023,04,14,0,0,0,0))
                .endDate(new Timestamp(2023,04,30,0,0,0,0))
                .days(20)
                .createDate(new Timestamp(2023,10,10,0,0,0,0))
                .updateDate(new Timestamp(2023,04,14,0,0,0,0))
                .company(new Company())
                .student(new Student())
                .facultySupervisor(new FacultySupervisor())
                .build();

        when(internshipRepository.save(any(Internship.class))).thenReturn(internshipToSave);

        var actual = internshipService.addInternship(internshipToSave);

        assertNotNull(actual);
        assertEquals(internshipToSave.getId(), actual.getId());
        assertEquals(internshipToSave.getStatus(), actual.getStatus());
        assertEquals(internshipToSave.getStartDate(), actual.getStartDate());
        assertEquals(internshipToSave.getEndDate(), actual.getEndDate());
        assertEquals(internshipToSave.getDays(), actual.getDays());
        assertEquals(internshipToSave.getCreateDate(), actual.getCreateDate());
        assertEquals(internshipToSave.getUpdateDate(), actual.getUpdateDate());
        assertEquals(internshipToSave.getCompany(), actual.getCompany());
        assertEquals(internshipToSave.getStudent(), actual.getStudent());
        assertEquals(internshipToSave.getFacultySupervisor(), actual.getFacultySupervisor());
        verify(internshipRepository).save(internshipToSave);
    }
}
