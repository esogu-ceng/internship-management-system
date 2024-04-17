package tr.edu.ogu.ceng.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.SettingRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Setting;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.IllegalArgumentException;

@Slf4j
@Service
@AllArgsConstructor
public class StudentService {
	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	private final FacultyService facultyService;
	private final FacultySupervisorService facultySupervisorService;
	private ModelMapper modelMapper;
	private EmailService emailService;
	private SettingRepository settingRepository;

	public Student getStudent(long id) {
		try {
			Student student = studentRepository.findById(id).orElse(null);
			if (student == null) {
				log.warn("There is no student with the entered ID.");
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			log.info("Student with ID {} has {} number: {}, {}. ", student.getId(), student.getStudentNo(),
					student.getName(), student.getSurname());

			return student;
		} catch (EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public Page<Student> getAllStudents(Pageable pageable) {
		try {
			log.info("Getting all students with pageable: {}", pageable);
			Page<Student> students = studentRepository.findAll(pageable);
			// Check if the student list is empty
			if (students.isEmpty()) {
				log.warn("The student list is empty.");
				return Page.empty();
			}

			return students;
		} catch (Exception e) {
			log.error("An error occurred while getting students: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public Student addStudent(Student student) {
		LocalDateTime now = LocalDateTime.now();
		student.setCreateDate(now);
		student.setUpdateDate(now);
		student.getUser().setUserType(UserType.STUDENT);
		student.getUser().setCreateDate(now);
		student.getUser().setUpdateDate(now);
		student.setUser(userRepository.save(student.getUser()));
		Student savedStudent = studentRepository.save(student);
		log.info("The student was successfully added: {}", savedStudent);

		return savedStudent;
	}

	@Transactional
	public Student addStudentAndSendMail(Student student) {
		Student savedStudent = addStudent(student);
		if (savedStudent != null) {
			String emailSubject = " Şifre Hatırlatıcı";
			String emailBody = "Sayın " + savedStudent.getName() + " " + savedStudent.getSurname() + ",\n\n"
					+ "Yeni şifrenizi aşağıda bulabilirsiniz:\n\n"
					+ "UserName: " + savedStudent.getUser().getEmail() + "\n\n" +
					"Şifre: " + savedStudent.getUser().getPassword() + "\n\n"
					+ "Lütfen şifrenizi güvende tuttuğunuzdan ve kimseyle paylaşmadığınızdan emin olun.\n\n"
					+ "Herhangi bir sorunuz veya endişeniz varsa, lütfen bizimle iletişime geçmekten çekinmeyin.\n\n"
					+ "İyi günler dileriz,\n";

			emailService.sendEmail(savedStudent.getUser().getEmail(), emailSubject, emailBody);
		}

		return savedStudent;

	}

	public Student updateStudent(Student studentController) {
		LocalDateTime now = LocalDateTime.now();

		if (studentController.getId() == null) {
			log.warn("Student ID cannot be null.");
			throw new IllegalArgumentException("Student ID cannot be null");
		}
		Student student = studentRepository.findById(studentController.getId())
				.orElseThrow(() -> new EntityNotFoundException("Student not found!"));

		UserType userTypeDto = student.getUser().getUserType();
		User user = student.getUser();
		user.setUserType(userTypeDto);
		user.setUpdateDate(now);
		user.setEmail(studentController.getUser().getEmail());
		// user.setPassword(studentController.getUser().getPassword());
		// user.setUsername(studentController.getUser().getUsername());

		studentController.setCreateDate(studentRepository.getReferenceById(student.getId()).getCreateDate());
		studentController.setUpdateDate(LocalDateTime.now());
		Student updatedStudent;
		try {
			updatedStudent = studentRepository.save(studentController);

			log.info("Student with ID {} has been successfully updated. Email: {}, Student No: {}", updatedStudent.getId(),
					updatedStudent.getUser().getEmail(), updatedStudent.getStudentNo());

		} catch (Exception e) {
			log.error("Error occurred while updating student: {}", e.getMessage());
			throw e;
		}
		return updatedStudent;
	}

	@Transactional
	public boolean deleteStudent(long id) {
		if (!studentRepository.existsById(id)) {
			log.warn("Student with ID {} not found.", id);
			return true;
		}
		studentRepository.deleteById(id);
		log.info("Student with ID {} has been successfully deleted.", id);
		return true;
	}

	/**
	 * Search student by name, surname or student number.
	 * 
	 * @param pageable
	 * @param keyword
	 * @return Page<StudentResponseDto>
	 */
	public Page<Student> searchStudent(Pageable pageable, String keyword) {
		try {

			log.info("Getting students by name, surname or studentNo: {} with pageable: {}", keyword, pageable);
			Page<Student> students = studentRepository.findByNameOrSurnameOrStudentNo(keyword, keyword, keyword,
					pageable);

			return students;
		} catch (Exception e) {
			log.error("An error occurred while getting students by name: {}: {}", keyword, e.getMessage());
			throw e;
		}
	}

	public Student getStudentByUserId(Long id) {
		try {
			Student student = studentRepository.findByUserId(id);
			log.info("Student with ID {} has email: {}", student.getId(), student.getUser().getEmail());
			return student;
		} catch (Exception e) {
			log.error("An error occurred while getting student by user ID: {}", e.getMessage());
			throw e;
		}
	}

	public StudentResponseDto registerAsStudent(StudentDto request) {

		FacultyDto facultyDto = facultyService.getFacultyById(request.getFaculty().getId());
		System.out.println("\n\n\n");
		System.out.println(request);
		System.out.println("\n\n\n");
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setUserType(UserType.STUDENT);
		user = userService.addUser(user);

		ModelMapper modelMapper = new ModelMapper();
		Student student = modelMapper.map(request, Student.class);

		student.setUser(user);
		student.setFaculty(modelMapper.map(facultyDto, Faculty.class));

		student.setUpdateDate(LocalDateTime.now());
		student.setCreateDate(LocalDateTime.now());
		studentRepository.save(student);

		log.info("Student registered with ID: {} and email: {}", student.getId(), student.getUser().getEmail());

		StudentResponseDto response = modelMapper.map(student, StudentResponseDto.class);
		return response;

	}

	public Page<Student> getAllStudentsByFacultySupervisorId(Long faculty_supervisor_id, Pageable pageable) {
		FacultySupervisorResponseDto facultySupervisorDto = facultySupervisorService
				.getFacultySupervisor(faculty_supervisor_id);
		Long faculty_id = facultySupervisorDto.getFacultyId();
		try {
			Page<Student> students = studentRepository.findAllByFacultyId(faculty_id, pageable);
			if (students.isEmpty()) {
				log.warn("The student list is empty.");
			}

			log.info("Getting all students with pageable: {}", pageable);
			return students;
		} catch (Exception e) {
			log.error("An error occured while getting students: {}", e.getMessage());
			throw e;
		}
	}

	public Long countStudents() {
		return studentRepository.count();
	}

	public String uploadCvToFileSystem(String studentNo, MultipartFile file) throws IOException {

		Optional<Student> student = studentRepository.findByStudentNo(studentNo);
		Setting setting = settingRepository.findByKey("cv_directory");
		String FOLDER_PATH = setting.getValue() + "/";

		if (student.isEmpty()) {
			log.warn("Student not found studentNo: {}", studentNo);
			throw new EntityNotFoundException("Öğrenci numarası bulunamadı lütfen geçerli öğrenci numarası giriniz.");
		}

		if (!file.getContentType().equals("application/pdf")) {
			log.warn("File is not pdf file!");
			throw new IllegalArgumentException("Dosya formatı sadece pdf olmalıdır!" + file.getContentType());
		}

		String filePath = FOLDER_PATH + student.get().getStudentNo() + ".pdf";

		// Create directories if they do not exist
		new File(FOLDER_PATH).mkdirs();

		student.get().setCvPath(filePath);

		studentRepository.save(student.get());

		file.transferTo(new File(filePath).toPath());
		log.info("File uploaded successfully for this studentNo : {}", student.get().getStudentNo());
		return "Dosya başarıyla kaydedildi. : " + filePath;
	}

	public byte[] downloadCvFromFileSystem(String studentNo) throws IOException {
		Optional<Student> student = studentRepository.findByStudentNo(studentNo);

		if (student.isEmpty()) {
			log.warn("Student not found studentNo: {}", studentNo);
			throw new EntityNotFoundException("Öğrenci numarası bulunamadı lütfen geçerli öğrenci numarası giriniz.");
		}
		String filePath = student.get().getCvPath();

		if (filePath == null) {
			throw new EntityNotFoundException("Öğrencinin CV'si bulunamadı.");
		}
		byte[] cv = Files.readAllBytes(new File(filePath).toPath());
		log.info("File downloaded successfully for this studentNo : {}", student.get().getStudentNo());
		return cv;
	}

}
