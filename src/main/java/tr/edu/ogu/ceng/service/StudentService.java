package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.dto.requests.StudentRequestDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;

@Slf4j
@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final FacultyRepository facultyRepository;
	private final FacultyService facultyService;
	private final FacultySupervisorService facultySupervisorService;
	private ModelMapper modelMapper;

	public StudentResponseDto getStudent(long id) {
		try {
			Student student = studentRepository.findById(id).orElse(null);
			if (student == null) {
				log.warn("Student with ID {} not found.", id);
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			log.info("Student with ID {} has {} number: {}, {}. ", student.getId(), student.getStudentNo(),
					student.getName(), student.getSurname());
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(student, StudentResponseDto.class);
		} catch (EntityNotFoundException e) {
			log.error("An error occurred while getting student: {}", e.getMessage());
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting all students with pageable: {}", pageable);
			Page<Student> students = studentRepository.findAll(pageable);
			// Check if the student list is empty
			if (students.isEmpty()){
				log.warn("The student list is empty.");
				return Page.empty();
			}
			Page<StudentResponseDto> studentDtos = students
					.map(student -> modelMapper.map(student, StudentResponseDto.class));
			return studentDtos;
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
		log.info("Student with ID {} has been successfully added. Email: {}, Student No: {}", savedStudent.getId(),
				savedStudent.getUser().getEmail(), savedStudent.getStudentNo());

		return savedStudent;
	}

	public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto) {
		modelMapper = new ModelMapper();
		LocalDateTime now = LocalDateTime.now();

		if (studentRequestDto.getId() == null) {
			log.warn("Student ID cannot be null.");
			throw new IllegalArgumentException("Student ID cannot be null");
		}
		Student student = studentRepository.findById(studentRequestDto.getId())
				.orElseThrow(() -> new EntityNotFoundException("Student not found!"));

		UserType userTypeDto = student.getUser().getUserType();
		User user = student.getUser();
		user.setUserType(userTypeDto);
		user.setUpdateDate(now);
		user.setEmail(studentRequestDto.getUser().getEmail());
		// user.setPassword(studentRequestDto.getUser().getPassword());
		// user.setUsername(studentRequestDto.getUser().getUsername());

		student = modelMapper.map(studentRequestDto, Student.class);
		student.setUser(userService.saveUser(user));

		student.setCreateDate(studentRepository.getById(student.getId()).getCreateDate());
		student.setUpdateDate(LocalDateTime.now());
		Student updatedStudent;
		try {
			updatedStudent = studentRepository.save(student);

			log.info("Student with ID {} has been successfully updated. Email: {}, Student No: {}", updatedStudent.getId(),
					updatedStudent.getUser().getEmail(), updatedStudent.getStudentNo());

		} catch (Exception e) {
			log.error("Error occurred while updating student: {}", e.getMessage());
			throw e;
		}
		return modelMapper.map(updatedStudent, StudentResponseDto.class);
	}

	@Transactional
	public boolean deleteStudent(long id) {
		if (!studentRepository.existsById(id)) {
			log.warn("Student with ID {} not found.", id);
			return false;
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
	public Page<StudentResponseDto> searchStudent(Pageable pageable, String keyword) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting students by name, surname or studentNo: {} with pageable: {}", keyword, pageable);
			Page<Student> students = studentRepository.findByNameOrSurnameOrStudentNo(keyword, keyword, keyword,
					pageable);
			Page<StudentResponseDto> studentResponseDtos = students
					.map(student -> modelMapper.map(student, StudentResponseDto.class));
			return studentResponseDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting students by name: {}: {}", keyword, e.getMessage());
			throw e;
		}
	}

	public StudentDto getStudentByUserId(Long id) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Student student = studentRepository.findByUserId(id);
			log.info("Student with ID {} has email: {}", student.getId(), student.getUser().getEmail());
			return modelMapper.map(student, StudentDto.class);
		} catch (Exception e) {
			log.error("An error occurred while getting student by user ID: {}", e.getMessage());
			throw e;
		}
	}

	public StudentResponseDto registerAsStudent(StudentDto request) {

		FacultyDto facultyDto = facultyService.getFacultyById(request.getFaculty().getId());

		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setUserType(UserType.STUDENT);
		user = userService.saveUser(user);

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

	public Page<StudentResponseDto> getAllStudentsByFacultySupervisorId(Long faculty_supervisor_id, Pageable pageable) {
		FacultySupervisorResponseDto facultySupervisorDto = facultySupervisorService
				.getFacultySupervisor(faculty_supervisor_id);
		Long faculty_id = facultySupervisorDto.getFacultyId();
		try {
			ModelMapper modelMapper = new ModelMapper();
			Page<Student> students = studentRepository.findAllByFacultyId(faculty_id, pageable);
			if (students.isEmpty()) {
				log.warn("The student list is empty.");

			}
			Page<StudentResponseDto> studentDtos = students
					.map(student -> modelMapper.map(student, StudentResponseDto.class));
			log.info("Getting all students with pageable: {}", pageable);
			return studentDtos;
		} catch (Exception e) {
			log.error("An error occured while getting students: {}", e.getMessage());
			throw e;
		}
	}

}
