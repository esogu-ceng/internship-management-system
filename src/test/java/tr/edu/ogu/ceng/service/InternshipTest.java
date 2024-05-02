package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.CompanySupervisorRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.InternshipEvaluateFormRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
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

public class InternshipTest {
	@Mock
	InternshipRepository internshipRepository;

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

	@Mock
	InternshipService internshipService;

	@Mock
	InternshipEvaluateFormRepository internshipEvaluateFormRepository;

	@Mock
	CompanyService companyService;
	@Mock
	StudentService studentService;

	MessageResource messageResource;

	InternshipStatus status = InternshipStatus.APPLIED;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		internshipService = new InternshipService(internshipRepository, companySupervisorRepository,
				facultySupervisorRepository, new ModelMapper(), messageResource, authService,
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

}
