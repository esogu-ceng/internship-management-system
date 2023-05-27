package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.dao.InternshipRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRegistryRequestDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.model.InternshipRegistry;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;

public class InternshipRegistryTest {

	@Mock
	InternshipRegistryRepository internshipRegistryRepository;

	@Mock
	InternshipRegistryService internshipRegistryService;

	@Mock
	InternshipRepository internshipRepository;

	@Mock
	StudentRepository studentRepository;

	@Mock
	CompanyRepository companyRepository;

	@Mock
	FacultySupervisorRepository facultySupervisorRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	FacultyRepository facultyRepository;

	InternshipStatus status = InternshipStatus.APPROVED;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		internshipRegistryService = new InternshipRegistryService(internshipRegistryRepository,
				internshipRegistryService, internshipRepository, studentRepository, companyRepository,
				facultySupervisorRepository, userRepository, facultyRepository, new ModelMapper());
	}

	@Test
	public void should_save_one_internshipRegistry() {

		LocalDateTime localDateTime = LocalDateTime.now();

		var modelCompany = Company.builder().id(1L).name("Test").address("Test").phoneNumber("Test").faxNumber("Test")
				.email("Test@test.com").scope("Test").description("Test").createDate(localDateTime)
				.updateDate(localDateTime).build();
		var modelUser = User.builder().id(3L).username("Username").password("password").email("email")
				.userType(UserType.FACULTYSUPERVISOR).createDate(localDateTime).updateDate(localDateTime).build();
		var modelFaculty = Faculty.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime)
				.build();
		var modelFacultySupervisor = FacultySupervisor.builder().id(4L).name("Name").surname("Surname")
				.phoneNumber("Phone").supervisorNo("No").createDate(localDateTime).updateDate(localDateTime)
				.user(modelUser).faculty(modelFaculty).build();
		var modelStudent = Student.builder().id(6L).name("test").surname("test").tckn("test").studentNo("test")
				.grade("test").phoneNumber("test").province("test").subprovince("test").zipCode("test")
				.motherName("test").fatherName("test").birthPlace("test")
				.birthDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0)).idCardSerialNo("test").idRegisterProvince("test")
				.idRegisterSubprovince("test").idRegisterStreetVillage("test").idRegisterVolumeNo("test")
				.idRegisterFamilySerialNo("test").idRegistryOffice("test").idRegistryReason("test")
				.createDate(localDateTime).updateDate(localDateTime).faculty(modelFaculty).user(modelUser).build();
		var modelInternship = Internship.builder().id(1L).status(status)
				.startDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0)).endDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.days(1).student(modelStudent).company(modelCompany).facultySupervisor(modelFacultySupervisor).build();
		var modelInternshipRegistry = InternshipRegistry.builder().id(1L).filePath("C:/Users/root/test")
				.name("internshipRegistry1").type("pdf").date(new Timestamp(2023, 04, 12, 0, 0, 0, 0))
				.internship(modelInternship).build();

		when(studentRepository.save(any(Student.class))).thenReturn(modelStudent);
		when(companyRepository.save(any(Company.class))).thenReturn(modelCompany);
		when(userRepository.save(any(User.class))).thenReturn(modelUser);
		when(facultyRepository.save(any(Faculty.class))).thenReturn(modelFaculty);
		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenReturn(modelInternshipRegistry);
		when(internshipRepository.save(any(Internship.class))).thenReturn(modelInternship);
		when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(modelFacultySupervisor);

		var DtoFaculty = FacultyDto.builder().id(1L).name("Faculty").createDate(localDateTime).updateDate(localDateTime)
				.build();

		var DtoUser = UserDto.builder().id(3L).username("Username").password("password").email("email")
				.userType(UserType.FACULTYSUPERVISOR).createDate(localDateTime).updateDate(localDateTime).build();

		var DtoFacultySupervisor = FacultySupervisorDto.builder().id(4L).name("Name").surname("Surname")
				.phoneNumber("Phone").supervisorNo("No").user(DtoUser).faculty(DtoFaculty).build();

		var Dtostudent = StudentDto.builder().id(6L).name("test").surname("test").tckn("test").studentNo("test")
				.grade("test").phoneNumber("test").province("test").subprovince("test").zipCode("test")
				.motherName("test").fatherName("test").birthPlace("test")
				.birthDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0)).idCardSerialNo("test").idRegisterProvince("test")
				.idRegisterSubprovince("test").idRegisterStreetVillage("test").idRegisterVolumeNo("test")
				.idRegisterFamilySerialNo("test").idRegistryOffice("test").idRegistryReason("test")
				.createDate(localDateTime).updateDate(localDateTime).faculty(DtoFaculty).build();

		var Dtocompany = CompanyDto.builder().id(1L).name("Test").address("Test").phoneNumber("Test").faxNumber("Test")
				.email("Test@test.com").scope("Test").description("Test").createDate(localDateTime)
				.updateDate(localDateTime).build();

		var Dtointernship = InternshipDto.builder().id(1L).status(status)
				.startDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0)).endDate(new Timestamp(2000, 01, 01, 0, 0, 0, 0))
				.days(1).student(Dtostudent).company(Dtocompany).facultySupervisor(DtoFacultySupervisor).build();

		var DtointernshipRegistry = InternshipRegistryRequestDto.builder().id(1L).filePath("C:/Users/root/test")
				.name("internshipRegistry1").type("pdf").date(new Timestamp(2023, 04, 12, 0, 0, 0, 0))
				.internshipId(Dtointernship.getId()).build();

		var actual = internshipRegistryService.addInternshipRegistry(DtointernshipRegistry);

		assertNotNull(modelInternshipRegistry);
		assertEquals(modelInternshipRegistry.getId(), actual.getId());
		assertEquals(modelInternshipRegistry.getFilePath(), actual.getFilePath());
		assertEquals(modelInternshipRegistry.getName(), actual.getName());
		assertEquals(modelInternshipRegistry.getType(), actual.getType());
		assertEquals(modelInternshipRegistry.getDate(), actual.getDate());
		assertEquals(modelInternshipRegistry.getInternship().getId(), actual.getInternshipId());

	}

}
