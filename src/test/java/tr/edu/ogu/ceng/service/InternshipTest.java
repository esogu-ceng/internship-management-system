package tr.edu.ogu.ceng.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import tr.edu.ogu.ceng.dao.*;
import tr.edu.ogu.ceng.dto.*;

import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.security.AuthService;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

public class InternshipTest {
	@Mock
	InternshipRepository internshipRepository;
 @Mock
 ModelMapper modelMapper;
	@Mock
	AuthService authService;
	@Mock
	StudentRepository studentRepository;
	@Mock
	CompanySupervisorRepository companySupervisorRepository;
	@Mock
	CompanyRepository companyRepository;

	@Mock
	FacultySupervisorRepository facultySupervisorRepository;
	
	@InjectMocks
    InternshipEvaluateFormService internshipEvaluateFormService;
	
	@Mock
	InternshipService internshipService;

	@Mock
	InternshipEvaluateFormRepository internshipEvaluateFormRepository;

	@Mock
	MessageResource messageResource;



	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);

		internshipService = new InternshipService(internshipRepository, companySupervisorRepository,
				facultySupervisorRepository, modelMapper, messageResource, authService,
				internshipEvaluateFormRepository);
	}

	@Test
	public void is_internship_added_successfully() {

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

		when(studentRepository.save(any(Student.class))).thenReturn(modelStudent);
		when(companyRepository.save(any(Company.class))).thenReturn(modelCompany);
		when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(modelFacultySupervisor);
		when(internshipRepository.save(any(Internship.class))).thenReturn(modelInternship);

		// TODO @ Change when the InternshipRequestDto manipulated
		var Dtointernship = InternshipRequestDto.builder().id(1L).status(InternshipStatus.FACULTY_APPROVED)
				.startDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0)).endDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.days(1).studentId(1004L).companyId(9001L).facultySupervisorId(400L).build();
		ModelMapper modelMapper = new ModelMapper();
		InternshipService internshipService = new InternshipService(internshipRepository, companySupervisorRepository, facultySupervisorRepository, modelMapper, messageResource, authService, internshipEvaluateFormRepository);
		var actual = internshipService.addInternship(Dtointernship);

		assertNotNull(actual);
		assertEquals(modelInternship.getId(), actual.getId());
		assertEquals(modelInternship.getStatus(), actual.getStatus());
		assertEquals(modelInternship.getDays(), actual.getDays());
		assertEquals(modelInternship.getCompany().getId(), actual.getCompanyId());
		assertEquals(modelInternship.getStudent().getId(), actual.getStudent().getId());
		assertEquals(modelInternship.getFacultySupervisor().getId(), actual.getFacultySupervisorId());
	}

	@Test
	public void is_markInternshipCompleted_success_withEvaluation() throws Exception {

		// Mock data
		LocalDateTime now = LocalDateTime.now();
		Long internshipId = 1L;
		Long facultySupervisorId = 2L;
		Optional<Internship> internship = Optional.of(new Internship(internshipId, InternshipStatus.APPLIED, null, null, 0, now, now, null, null, null));
		Optional<FacultySupervisor> facultySupervisor = Optional
				.of(new FacultySupervisor(facultySupervisorId, "Name", "Surname", "Phone", "No", now, now, null, null));
		InternshipEvaluateForm companyEvaluation = new InternshipEvaluateForm(); // Mock company evaluation

		// Mock repositories (assuming you have mocks set up)
		when(internshipRepository.findById(internshipId)).thenReturn(internship);
		when(facultySupervisorRepository.findById(facultySupervisorId)).thenReturn(facultySupervisor);
		when(internshipEvaluateFormRepository.findByInternshipId(internshipId)).thenReturn(companyEvaluation);

		// Call the method
		boolean isCompleted = internshipService.markInternshipCompleted(facultySupervisorId, internshipId);

		// Assertions
		assertTrue(isCompleted);
		assertEquals(InternshipStatus.SUCCESS, internship.get().getStatus());
	}

	@Test
	public void is_markInternshipCompleted_failure_noEvaluation() throws Exception {

		// Mock data
		LocalDateTime now = LocalDateTime.now();
		Long internshipId = 1L;
		Long facultySupervisorId = 2L;
		Optional<Internship> internship = Optional.of(new Internship(internshipId, InternshipStatus.APPLIED, null, null, 0, now, now, null, null, null));
		Optional<FacultySupervisor> facultySupervisor = Optional
				.of(new FacultySupervisor(facultySupervisorId, "Name", "Surname", "Phone", "No", now, now, null, null));

		// Mock repositories (assuming you have mocks set up)
		when(internshipRepository.findById(internshipId)).thenReturn(internship);
		when(facultySupervisorRepository.findById(facultySupervisorId)).thenReturn(facultySupervisor);
		when(internshipEvaluateFormRepository.findByInternshipId(internshipId)).thenReturn(null); // No company evaluation

		// Expected exception
		Exception expectedException = new Exception("Internship is not evaluated by the company yet!");

		// Call the method with expected exception
		try {
			internshipService.markInternshipCompleted(facultySupervisorId, internshipId);
			fail("Expected exception not thrown!");
		} catch (Exception e) {
			assertEquals(expectedException.getMessage(), e.getMessage());
		}
	}
	@Test
	public void test_updateInternship_successfully_update() {
		// Mock data
		LocalDateTime now = LocalDateTime.now();
		Long internshipId = 1L;
		InternshipRequestDto internshipDto = InternshipRequestDto.builder()
				.id(internshipId)
				.status(InternshipStatus.FACULTY_APPROVED)
				.startDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.endDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.days(1)
				.studentId(1004L)
				.companyId(9001L)
				.facultySupervisorId(400L)
				.build();
		Internship existingInternship = new Internship(internshipId, InternshipStatus.APPLIED, null, null, 0, now, now, null, null, null);
		Internship updatedInternship = new Internship(internshipId, InternshipStatus.FACULTY_APPROVED, new Timestamp(2000, 01, 01, 0, 0, 0, 0), new Timestamp(2000, 01, 01, 0, 0, 0, 0), 1, now, now, null, null, null);
		InternshipResponseDto expectedResponse = InternshipResponseDto.builder()
				.id(internshipId)
				.status(InternshipStatus.FACULTY_APPROVED)
				.startDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.endDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.days(1)
				.companyId(9001L)
				.facultySupervisorId(400L)
				.createDate(now)
				.updateDate(now)
				.build();

		// Mock repository response
		when(internshipRepository.findById(internshipId)).thenReturn(Optional.of(existingInternship));
		when(internshipRepository.save(any(Internship.class))).thenReturn(updatedInternship);

		// Mock model mapper
		when(modelMapper.map(any(), eq(Internship.class))).thenReturn(updatedInternship);
		when(modelMapper.map(any(), eq(InternshipResponseDto.class))).thenReturn(expectedResponse);

		// Call the method
		InternshipResponseDto actual = internshipService.updateInternship(internshipDto);

		// Assertions
		assertNotNull(actual);
		assertEquals(expectedResponse, actual);
		verify(internshipRepository, times(1)).save(any(Internship.class));
		verify(modelMapper, times(1)).map(any(), eq(Internship.class));
		verify(modelMapper, times(1)).map(any(), eq(InternshipResponseDto.class));
	}

	@Test
	public void test_updateInternship_ThrowEntityNotFoundExceptionForNull() {
		// Mock data
		Long internshipId = 1L;
		InternshipRequestDto internshipDto = InternshipRequestDto.builder()
				.id(internshipId)
				.status(InternshipStatus.FACULTY_APPROVED)
				.startDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.endDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.days(1)
				.studentId(1004L)
				.companyId(9001L)
				.facultySupervisorId(400L)
				.build();

		// Mock repository response
		when(internshipRepository.findById(internshipId)).thenReturn(Optional.empty());

		// Call the method and assert exception
		assertThrows(EntityNotFoundException.class, () -> {
			internshipService.updateInternship(internshipDto);
		});

		// Verify interactions
		verify(internshipRepository, times(1)).findById(internshipId);
		verifyNoMoreInteractions(internshipRepository);
		verifyNoInteractions(modelMapper);
	}

	@Test
	public void test_deleteInternship_delete_successfully() {
		// Mock data
		Long internshipId = 1L;

		// Mock repository response
		when(internshipRepository.existsById(internshipId)).thenReturn(true);

		// Call the method
		boolean isDeleted = internshipService.deleteInternship(internshipId);

		// Verify interactions and assertions
		assertTrue(isDeleted);
		verify(internshipRepository, times(1)).existsById(internshipId);
		verify(internshipRepository, times(1)).deleteById(internshipId);
		verifyNoMoreInteractions(internshipRepository);
	}

	@Test
	public void test_deleteInternship_WhenInternshipNotExists() {
		Long id = 1L;
		when(internshipRepository.existsById(id)).thenReturn(false);

		boolean result = internshipService.deleteInternship(id);

		assertTrue(result);
		verify(internshipRepository, never()).deleteById(anyLong());



	}
	
	@Test
    public void testGetByInternshipId() {
        // Mock data
        Long internshipId = 1L;
        InternshipEvaluateForm internshipEvaluateForm = new InternshipEvaluateForm();
        when(internshipEvaluateFormRepository.findByInternshipId(internshipId)).thenReturn(internshipEvaluateForm);

        // Test
        InternshipEvaluateForm result = internshipEvaluateFormService.getByInternshipId(internshipId);

        // Assertion
        assertEquals(internshipEvaluateForm, result);
    }

	@Test
	public void testMapEntityToDto() {
	    // Mock data
	    InternshipEvaluateForm form = new InternshipEvaluateForm();
	    form.setId(1L);
	    form.setQuestion1(5);
	    form.setQuestion2(3);
	    form.setQuestion3(4);
	    form.setQuestion4(2);
	    form.setQuestion5(5);
	    form.setQuestion6(4);
	    form.setQuestion7(3);
	    form.setQuestion8(2);

	    // Test
	    InternshipEvaluateFormDto dto = internshipEvaluateFormService.mapEntityToDto(form);

	    // Assertion
	    assertEquals(form.getId(), dto.getId());
	    assertEquals(form.getInternship(), dto.getInternship());
	    assertEquals(form.getCompany(), dto.getCompany());
	    assertEquals(form.getQuestion1(), dto.getQuestion1());
	    assertEquals(form.getQuestion2(), dto.getQuestion2());
	    assertEquals(form.getQuestion3(), dto.getQuestion3());
	    assertEquals(form.getQuestion4(), dto.getQuestion4());
	    assertEquals(form.getQuestion5(), dto.getQuestion5());
	    assertEquals(form.getQuestion6(), dto.getQuestion6());
	    assertEquals(form.getQuestion7(), dto.getQuestion7());
	    assertEquals(form.getQuestion8(), dto.getQuestion8());
	}
	
    @Test
    public void testSaveInternshipEvalForm() {
        // Mock data
        InternshipEvaluateFormDto formDto = new InternshipEvaluateFormDto();
        formDto.setId(1L);
        formDto.setQuestion1(5);
        // Set other fields as necessary

        InternshipEvaluateForm existingForm = new InternshipEvaluateForm();
        existingForm.setId(1L);
        existingForm.setQuestion1(3);
        // Set other fields as necessary

        when(internshipEvaluateFormRepository.findById(formDto.getId())).thenReturn(Optional.of(existingForm));
        when(internshipEvaluateFormRepository.save(any(InternshipEvaluateForm.class))).thenReturn(existingForm);

        // Test
        InternshipEvaluateFormDto result = internshipEvaluateFormService.saveInternshipEvalForm(formDto);

        // Assertion
        assertEquals(existingForm.getId(), result.getId());
        assertEquals(formDto.getQuestion1(), result.getQuestion1());
        // Check other fields as well
    }
    
    @Test
	 public void testChangeInternshipStatus_NotFound() {
	        Long internshipId = 1L;
	        InternshipStatus newStatus = InternshipStatus.SUCCESS;

	        when(internshipRepository.existsById(internshipId)).thenReturn(false);
	        when(messageResource.getMessage("internshipRegistryNotFound")).thenReturn("Internship not found");

	        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
	            internshipService.chanceInternshipStatus(internshipId, newStatus);
	        });

	        assertEquals("Internship not found", exception.getMessage());
	    }

	    @Test
	    public void testChangeInternshipStatus_Success() {
	        Long internshipId = 1L;
	        InternshipStatus newStatus = InternshipStatus.SUCCESS;
	        LocalDateTime now = LocalDateTime.now();
	        Internship internship = new Internship();
	        internship.setId(internshipId);

	        when(internshipRepository.existsById(internshipId)).thenReturn(true);
	        when(internshipRepository.findById(internshipId)).thenReturn(java.util.Optional.of(internship));

	        InternshipStatus result = internshipService.chanceInternshipStatus(internshipId, newStatus);

	        assertEquals(newStatus, result);
	        assertEquals(newStatus, internship.getStatus());
	        assertTrue(internship.getUpdateDate().isAfter(now.minusSeconds(1)));
	        verify(internshipRepository, times(1)).save(internship);
	    }

	    @Test
	    public void testGetStudentByInternshipId_Success() {
	       
	    	LocalDateTime now = LocalDateTime.now();

	        
	        Student student = new Student();
	        student.setId(1L);
	        student.setName("John");
	        student.setSurname("Doe");
	        student.setStudentNo("1001");
	        student.setPhoneNumber("123-456-7890");
	        student.setBirthPlace("City");
	        student.setBirthDate(Timestamp.valueOf("2000-01-01 00:00:00"));

	        
	        Internship internship = new Internship();
	        internship.setId(1L);
	        internship.setStudent(student);

	        
	        when(internshipRepository.existsById(1L)).thenReturn(true);
	        when(internshipRepository.findById(1L)).thenReturn(Optional.of(internship));

	        
	        StudentResponseDto studentResponseDto = new StudentResponseDto();
	        studentResponseDto.setName(student.getName());
	        studentResponseDto.setSurname(student.getSurname());
	        

	        when(modelMapper.map(student, StudentResponseDto.class)).thenReturn(studentResponseDto);

	        StudentResponseDto result = internshipService.getStudentByInternshipId(1L);

	        assertNotNull(result);
	        assertEquals("John", result.getName());
	        assertEquals("Doe", result.getSurname());

	        verify(internshipRepository, times(1)).existsById(1L);
	        verify(internshipRepository, times(1)).findById(1L);
	    }

	    @Test
	    public void testGetStudentByInternshipId_ThrowsEntityNotFoundException() {
	        
	        Long nonExistentId = 999L;
	        when(internshipRepository.existsById(nonExistentId)).thenReturn(false);

	       
	        assertThrows(javax.persistence.EntityNotFoundException.class, () -> {
	           internshipService.getStudentByInternshipId(nonExistentId);
	        });
	    }

}

