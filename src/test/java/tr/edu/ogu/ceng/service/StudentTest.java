package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.time.temporal.ChronoUnit;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

public class StudentTest {

	@Mock
	StudentRepository studentRepository;
	@Mock
	StudentService studentService;

	@Mock
	UserRepository userRepository;
	@Mock
	UserService userService;

	@Mock
	SettingRepository settingRepository;
	@Mock
	FacultyService facultyService;
	@Mock
	FacultyRepository facultyRepository;
	@Mock
	FacultySupervisorService facultySupervisorService;

	@Mock
	EmailService emailService;
	@Mock
	ModelMapper modelMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		studentService = new StudentService(studentRepository, userRepository, userService, facultyService, facultySupervisorService,
				new ModelMapper(), emailService, settingRepository);
	}

	@Test
	public void when_resultIsNotNull_then_returnValidDTO() {
		when(studentRepository.findById(6L)).thenThrow(new EntityNotFoundException());

		assertThrows(EntityNotFoundException.class, () -> {
			studentService.getStudent(6L);
		});

	}

	@Test
	void should_save_one_student() {

		// FIXME This test case will be extended to mock all used repository methods
		// like facultyRepository.get etc.
		LocalDateTime dateTime = LocalDateTime.now();
		var savedUser = new User(1002L, "passwordHash", "", null, dateTime, dateTime, null, false);
		var SavedStudent = new Student(6L, "test", "test", "test", "test", null, null, null,
				new Timestamp(2000, 01, 01, 0, 0, 0, 0), dateTime, dateTime, savedUser, new Faculty(), "address", null);

		var user = new User(1002L, "passwordHash", "", null, dateTime, dateTime, null, false);
		user.setId(null);
		var student = new Student(6L, "test", "test", "test", "test", null, null, null,
				new Timestamp(2000, 01, 01, 0, 0, 0, 0), dateTime, dateTime, savedUser, new Faculty(), "address", null);
		student.setId(null);

		when(userRepository.save(any(User.class))).thenReturn(savedUser);
		when(studentRepository.save(any(Student.class))).thenReturn(SavedStudent);

		var actual = studentService.addStudent(student);

		assertNotNull(actual);
		assertEquals(student.getName(), actual.getName());
		assertEquals(student.getSurname(), actual.getSurname());
		assertEquals(student.getStudentNo(), actual.getStudentNo());
		assertEquals(student.getGrade(), actual.getGrade());
		assertEquals(student.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(student.getBirthPlace(), actual.getBirthPlace());
		assertEquals(student.getBirthDate(), actual.getBirthDate());
		assertEquals(student.getAddress(), actual.getAddress());

	}

	@Test
	public void testGetStudentWhenExists() {
		// Prepare
		long studentId = 1L;
		Student student = new Student();
		student.setId(studentId);
		student.setStudentNo("12345");
		student.setName("John");
		student.setSurname("Doe");

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

		// Execute
		Student result = studentService.getStudent(studentId);

		// Verify
		assertNotNull(result);
		assertEquals(studentId, result.getId());
		assertEquals("12345", result.getStudentNo());
		assertEquals("John", result.getName());
		assertEquals("Doe", result.getSurname());
	}

	@Test
	public void testGetStudentWhenNotExists() {
		// Prepare
		long studentId = 1L;
		when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

		// Verify
		assertThrows(EntityNotFoundException.class, () -> {
			studentService.getStudent(studentId);
		});
	}
	@Test
	public void testGetAllStudents() {
		// Prepare
		Pageable pageable = Pageable.unpaged();
		Student student1 = new Student();
		Student student2 = new Student();
		Page<Student> studentsPage = new PageImpl<>(List.of(student1, student2));

		when(studentRepository.findAll(pageable)).thenReturn(studentsPage);

		// Execute
		Page<Student> result = studentService.getAllStudents(pageable);

		// Verify
		assertNotNull(result);
		assertEquals(2, result.getContent().size());
	}

	@Test
	public void testAddStudent() {
		// Prepare
		LocalDateTime now = LocalDateTime.now();
		Student student = new Student();
		student.setName("Jane");
		student.setSurname("Doe");
		student.setCreateDate(now);
		student.setUpdateDate(now);

		when(studentRepository.save(any(Student.class))).thenReturn(student);

		// Execute
		Student result = studentService.addStudent(student);

		// Verify
		assertNotNull(result);
		assertEquals("Jane", result.getName());
		assertEquals("Doe", result.getSurname());

		assertEquals(now.truncatedTo(ChronoUnit.SECONDS), result.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
		assertEquals(now.truncatedTo(ChronoUnit.SECONDS), result.getUpdateDate().truncatedTo(ChronoUnit.SECONDS));
	}

	@Test
	void should_delete_student() {
		// Öğrenci oluştur
		Student student = new Student();
		student.setId(1L);

		// Öğrenciyi veritabanına kaydet
		when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

		// Öğrenciyi sil
		studentService.deleteStudent(1L);

		// Öğrencinin artık veritabanında olmadığını doğrula
		when(studentRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> {
			studentService.getStudent(1L);
		});
	}

	@Test
	void should_upload_cv_to_file_system() throws IOException {
		// Öğrenciyi mockla
		Student student = new Student();
		student.setStudentNo("12345");

		// Dosyayı mockla
		MultipartFile file = new MockMultipartFile("cv", "cv.pdf", "application/pdf", "pdf data".getBytes());

		// Ayarı mockla
		Setting setting = new Setting();
		setting.setValue("/tmp");

		// studentRepository ve settingRepository'nin davranışını tanımla
		when(studentRepository.findByStudentNo("12345")).thenReturn(Optional.of(student));
		when(settingRepository.findByKey("cv_directory")).thenReturn(setting);

		// Test edilen metodu çağır
		String result = studentService.uploadCvToFileSystem("12345", file);

		// Sonucu doğrula
		String expectedMessage = "Dosya başarıyla kaydedildi. : /tmp/12345.pdf";
		assertEquals(expectedMessage, result);
	}

	@Test
	void should_download_cv_from_file_system() throws IOException {
		// Öğrenciyi mockla
		Student student = new Student();
		student.setStudentNo("12345");
		student.setCvPath(System.getProperty("java.io.tmpdir") + "/12345.pdf");

		// studentRepository'nin davranışını tanımla
		when(studentRepository.findByStudentNo("12345")).thenReturn(Optional.of(student));

		// Dosya sistemine bir dosya oluştur
		Files.write(Paths.get(student.getCvPath()), "pdf data".getBytes());

		// Test edilen metodu çağır
		byte[] result = studentService.downloadCvFromFileSystem("12345");

		// Sonucu doğrula
		assertArrayEquals("pdf data".getBytes(), result);

		// Dosya sistemini temizle
		Files.delete(Paths.get(student.getCvPath()));
	}

}
