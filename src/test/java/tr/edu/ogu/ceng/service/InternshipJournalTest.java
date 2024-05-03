package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.Console;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.InternshipJournalsRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.CompanySupervisorRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dto.InternshipJournalsDto;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.model.InternshipJournal;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import java.util.List;


public class InternshipJournalTest {
    
    @Mock
    private InternshipJournalsRepository internshipJournalsRepository;
    @Mock
    private InternshipRepository internshipRepository;
    @Mock
    private CompanySupervisorRepository companySupervisorRepository;
    @Mock 
    private StudentRepository studentRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FacultySupervisorRepository facultySupervisorRepository;

    @Mock
    private InternshipJournalsService internshipJournalsService;
    @Mock
    private InternshipService internshipService;
    @Mock
    private CompanySupervisorService companySupervisorService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MessageResource messageResource;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        internshipJournalsService = new InternshipJournalsService(internshipJournalsRepository, internshipRepository, modelMapper);
    }

    @Test
    public void testAddInternshipJournal() {
    
        LocalDateTime localDateTime = LocalDateTime.now();
    
        var modelCompany = new Company(1L, "Test", "Test", "Test", "Test", "Test", "Test", "Test", localDateTime,
                localDateTime);
        var modelUser = new User(3L, "password", "email", UserType.FACULTYSUPERVISOR, localDateTime,
                localDateTime, null, false);
        var modelFaculty = new Faculty(1L, "Faculty", localDateTime, localDateTime);
        var modelFacultySupervisor = new FacultySupervisor(4L, "Name", "Surname", "Phone", "No", localDateTime,
                localDateTime, modelUser, modelFaculty);
    
        var modelStudent = new Student(6L, "test", "test", "test", "test", "test", "test", "test", null, localDateTime,
                localDateTime, null, modelFaculty, "address", null);
    
        var modelInternship = new Internship(1L, InternshipStatus.FACULTY_APPROVED, null, null, 0, localDateTime, localDateTime,
                modelStudent, modelCompany, modelFacultySupervisor);
    
        var modelCompanySupervisor = new CompanySupervisor(1L, "Test", "Test", "Test", localDateTime, localDateTime, modelCompany, modelUser);
    
        Timestamp startingTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.now());
    
        var modelInternshipJournal = new InternshipJournal(1L, "Test", "Test", 2L, startingTimestamp, endTimestamp, localDateTime, localDateTime, modelInternship, modelCompanySupervisor, false);
            
        when(internshipJournalsRepository.save(any(InternshipJournal.class))).thenReturn(modelInternshipJournal);
    
 
        var newInternshipJournal = new InternshipJournal(1L, "Test", "Test", 2L, startingTimestamp, endTimestamp, localDateTime, localDateTime, modelInternship, modelCompanySupervisor, false);
    
        var actual = internshipJournalsService.addInternshipJournal(newInternshipJournal);

        assertNotNull(actual);
    
        assertEquals(modelInternshipJournal.getId(), actual.getId());
        assertEquals(modelInternshipJournal.getUnitName(), actual.getUnitName());
        assertEquals(modelInternshipJournal.getJournal(), actual.getJournal());
        assertEquals(modelInternshipJournal.getOperationTime(), actual.getOperationTime());
        assertEquals(modelInternshipJournal.getStartingDate(), actual.getStartingDate());
        assertEquals(modelInternshipJournal.getEndDate(), actual.getEndDate());
        assertEquals(modelInternshipJournal.getCreateDate(), actual.getCreateDate());
        assertEquals(modelInternshipJournal.getUpdateDate(), actual.getUpdateDate());
        assertEquals(modelInternshipJournal.getInternship().getId(), actual.getSupervisor().getId());
        assertEquals(modelInternshipJournal.getSupervisor().getId(), actual.getInternship().getId());
        assertEquals(modelInternshipJournal.isConfirmation(), actual.isConfirmation());
    }

    @Test
    public void testGetInternshipJournalById(){

        Long journalId = 1L;

        LocalDateTime localDateTime = LocalDateTime.now();
        
        var modelCompany = new Company(1L, "Test", "Test", "Test", "Test", "Test", "Test", "Test", localDateTime,
                localDateTime);
        var modelUser = new User(3L, "password", "email", UserType.FACULTYSUPERVISOR, localDateTime,
                localDateTime, null, false);
        var modelFaculty = new Faculty(1L, "Faculty", localDateTime, localDateTime);
        var modelFacultySupervisor = new FacultySupervisor(4L, "Name", "Surname", "Phone", "No", localDateTime,
                localDateTime, modelUser, modelFaculty);
    
        var modelStudent = new Student(6L, "test", "test", "test", "test", "test", "test", "test", null, localDateTime,
                localDateTime, null, modelFaculty, "address", null);
    
        var modelInternship = new Internship(1L, InternshipStatus.FACULTY_APPROVED, null, null, 0, localDateTime, localDateTime,
                modelStudent, modelCompany, modelFacultySupervisor);
    
        var modelCompanySupervisor = new CompanySupervisor(1L, "Test", "Test", "Test", localDateTime, localDateTime, modelCompany, modelUser);
    
        Timestamp startingTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.now());
    
        var modelInternshipJournal = new InternshipJournal(1L, "Test", "Test", 2L, startingTimestamp, endTimestamp, localDateTime, localDateTime, modelInternship, modelCompanySupervisor, false);
            
        when(internshipJournalsRepository.findById(journalId)).thenReturn(Optional.of(modelInternshipJournal));
        
        var actual = internshipJournalsService.getInternshipJournalById(journalId);

        assertEquals(journalId, actual.getId());
        assertEquals("Test", actual.getUnitName());
        assertEquals("Test", actual.getJournal());
        assertEquals(2L, actual.getOperationTime());
        assertEquals(startingTimestamp, actual.getStartingDate());
        assertEquals(endTimestamp, actual.getEndDate());
        assertEquals(localDateTime, actual.getCreateDate());
        assertEquals(localDateTime, actual.getUpdateDate());
        assertEquals(modelInternship, actual.getInternship());
        assertEquals(modelCompanySupervisor, actual.getSupervisor());
        assertEquals(false, actual.isConfirmation());
    
    }

    @Test
    public void testGetAllInternshipJournalsById() {
        
        Long internshipId = 1L;

        LocalDateTime localDateTime = LocalDateTime.now();

        var modelCompany = new Company(1L, "Test", "Test", "Test", "Test", "Test", "Test", "Test", localDateTime,
                localDateTime);
        var modelUser = new User(3L, "password", "email", UserType.FACULTYSUPERVISOR, localDateTime,
                localDateTime, null, false);
        var modelFaculty = new Faculty(1L, "Faculty", localDateTime, localDateTime);
        var modelFacultySupervisor = new FacultySupervisor(4L, "Name", "Surname", "Phone", "No", localDateTime,
                localDateTime, modelUser, modelFaculty);

        var modelStudent = new Student(6L, "test", "test", "test", "test", "test", "test", "test", null, localDateTime,
                localDateTime, null, modelFaculty, "address", null);

        var modelInternship = new Internship(1L, InternshipStatus.FACULTY_APPROVED, null, null, 0, localDateTime, localDateTime,
                modelStudent, modelCompany, modelFacultySupervisor);

        var modelCompanySupervisor = new CompanySupervisor(1L, "Test", "Test", "Test", localDateTime, localDateTime, modelCompany, modelUser);

        Timestamp startingTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.now());

        var modelInternshipJournal = new InternshipJournal(1L, "Test", "Test", 2L, startingTimestamp, endTimestamp, localDateTime, localDateTime, modelInternship, modelCompanySupervisor, false);

        when(internshipJournalsRepository.findAllJournalByInternshipId(internshipId)).thenReturn(List.of(modelInternshipJournal));

        var actual = internshipJournalsService.getAllInternshipJournalsByInternshipId(internshipId);

        assertNotNull(actual);
        assertEquals(1, actual.size());

        var actualInternshipJournal = actual.get(0);

        assertEquals(modelInternshipJournal.getId(), actualInternshipJournal.getId());
        assertEquals(modelInternshipJournal.getUnitName(), actualInternshipJournal.getUnitName());
        assertEquals(modelInternshipJournal.getJournal(), actualInternshipJournal.getJournal());
        assertEquals(modelInternshipJournal.getOperationTime(), actualInternshipJournal.getOperationTime());
    }



}
