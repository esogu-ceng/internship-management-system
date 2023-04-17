package tr.edu.ogu.ceng.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.User;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FacultySupervisorTest {

    @Mock
    FacultySupervisorRepository facultySupervisorRepository;
    FacultySupervisorService facultySupervisorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        facultySupervisorService = new FacultySupervisorService(facultySupervisorRepository);
    }

    @Test
    void is_faculty_supervisor_added_successfully() {
        var facultySupervisorToSave = FacultySupervisor.builder()
                .name("Faculty Supervisor")
                .surname("test")
                .phoneNumber("test")
                .supervisorNo("test")
                .createDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
                .updateDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
                .user(new User())
                .faculty(new Faculty())
                .build();

        when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(facultySupervisorToSave);

        var actual = facultySupervisorService.saveFacultySupervisor(facultySupervisorToSave);

        assertNotNull(actual);
        assertEquals(facultySupervisorToSave.getName(), actual.getName());
        assertEquals(facultySupervisorToSave.getSurname(), actual.getSurname());
        assertEquals(facultySupervisorToSave.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(facultySupervisorToSave.getSupervisorNo(), actual.getSupervisorNo());
        assertEquals(facultySupervisorToSave.getCreateDate(), actual.getCreateDate());
        assertEquals(facultySupervisorToSave.getUpdateDate(), actual.getUpdateDate());
        assertEquals(facultySupervisorToSave.getUser(), actual.getUser());
        assertEquals(facultySupervisorToSave.getFaculty(), actual.getFaculty());

        verify(facultySupervisorRepository).save(facultySupervisorToSave);
    }
}
