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
	private final FacultyRepository facultyRepository;
	private final FacultyService facultyService;
	private ModelMapper modelMapper;

	public StudentResponseDto getStudent(long id) {
		try {
			Student student = studentRepository.findById(id).orElse(null);
			if (student == null) {
				log.warn("There is no student with the entered ID.");
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			log.info("Student with ID {} has {} number: {}, {}. ", student.getId(), student.getStudentNo(),
					student.getName(), student.getSurname());
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(student, StudentResponseDto.class);
		} catch (EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting all students with pageable: {}", pageable);
			Page<Student> students = studentRepository.findAll(pageable);
			if (studentRepository.findAll() == null) {
				log.warn("The student list is empty.");
				return null;
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
	public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) {
		modelMapper = new ModelMapper();
		LocalDateTime now = LocalDateTime.now();

		// We need to save user before student.
		User user = modelMapper.map(studentRequestDto.getUser(), User.class);
		user.setUserType(UserType.STUDENT);
		user.setCreateDate(now);
		user.setUpdateDate(now);

		Student student = modelMapper.map(studentRequestDto, Student.class);
		student.setUser(userRepository.save(user));// FIXME instead, do we need to call to Service method?
													// But the Service method needs to get DTO. Or are there
													// any other approaches to persist the User?
		student.setCreateDate(now);
		student.setUpdateDate(now);

		Student savedStudent = studentRepository.save(student);
		log.info("The student was successfully added: {}", savedStudent);

		return modelMapper.map(savedStudent, StudentResponseDto.class);
	}

	public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto) {
		modelMapper = new ModelMapper();
		LocalDateTime now = LocalDateTime.now();

		if (studentRequestDto.getId() == null) {
			throw new IllegalArgumentException("Student ID cannot be null");
		}
		Student student = studentRepository.findById(studentRequestDto.getId())
				.orElseThrow(() -> new EntityNotFoundException("Student not found!"));

		UserType userTypeDto = student.getUser().getUserType();
		User user = student.getUser();
		user.setUserType(userTypeDto);
		user.setUpdateDate(now);
		user.setEmail(studentRequestDto.getUser().getEmail());
		user.setPassword(studentRequestDto.getUser().getPassword());
		user.setUsername(studentRequestDto.getUser().getUsername());

		student = modelMapper.map(studentRequestDto, Student.class);
		student.setUser(userRepository.save(user));

		student.setCreateDate(studentRepository.getById(student.getId()).getCreateDate());
		student.setUpdateDate(LocalDateTime.now());
		Student updatedStudent;
		try {
			updatedStudent = studentRepository.save(student);
			log.info("Student updated: {}", updatedStudent);
		} catch (Exception e) {
			log.error("Error occurred while updating student: {}", e.getMessage());
			throw e;
		}
		return modelMapper.map(updatedStudent, StudentResponseDto.class);
	}

	@Transactional
	public boolean deleteStudent(long id) {
		if (!studentRepository.existsById(id)) {
			log.warn("The deletion could not be performed as there is no student with the entered ID.");
			return false;
		}
		studentRepository.deleteById(id);
		log.info("The student with the entered ID has been deleted.");
		return true;
	}

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
			Student student = studentRepository.getReferenceById(id);
			return modelMapper.map(student, StudentDto.class);
		} catch (Exception e) {
			log.error("An error occurred while getting students with given ID", e.getMessage());
			throw e;
		}
	}

	public StudentDto registerAsStudent(StudentDto request) {

		checkIfPasswordsMatchingValidation(request);

		FacultyDto facultyDto = facultyService.getFacultyById(request.getFaculty().getId());

		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setUserType(UserType.STUDENT);
		user = userRepository.save(user);

		ModelMapper modelMapper = new ModelMapper();
		Student student = modelMapper.map(request, Student.class);

		student.setUser(user);
		student.setFaculty(modelMapper.map(facultyDto, Faculty.class));

		student.setUpdateDate(LocalDateTime.now());
		student.setCreateDate(LocalDateTime.now());
		studentRepository.save(student);
		log.info("Registration successful.");

		StudentDto response = modelMapper.map(student, StudentDto.class);
		return response;

	}

	// This method checks if the entered password and confirmed password are the
	// same. If not, it throws a runtime exception.
	private void checkIfPasswordsMatchingValidation(StudentDto request) {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new RuntimeException("Passwords do not match!");

	}

}
