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
import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.enums.UserTypeEnum;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.model.UserType;

@Slf4j
@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final UserTypeRepository userTypeRepository;
	private final FacultyRepository facultyRepository;
	private final UserTypeService userTypeService;
	private final FacultyService facultyService;
	
	private ModelMapper modelMapper;

	public StudentDto getStudent(long id) {
		try {
			Student student = studentRepository.findById(id).orElse(null);
			if (student == null) {
				log.warn("There is no student with the entered ID.");
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			log.info("Student with ID {} has {} number: {}, {}. ", student.getId(), student.getStudentNo(),
					student.getName(), student.getSurname());
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(student, StudentDto.class);
		} catch (EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public Page<StudentDto> getAllStudents(Pageable pageable) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting all students with pageable: {}", pageable);
			Page<Student> students = studentRepository.findAll(pageable);
			if (studentRepository.findAll() == null) {
				log.warn("The student list is empty.");
				return null;
			}
			Page<StudentDto> studentDtos = students.map(student -> modelMapper.map(student, StudentDto.class));
			return studentDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting students: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional
	public StudentDto addStudent(StudentDto studentDto) {
		Student student = modelMapper.map(studentDto, Student.class);
		// need to add a new user to the DB
		User user = student.getUser();
		LocalDateTime now = LocalDateTime.now();

		user.setCreateDate(now);
		user.setUpdateDate(now);
		user.setUserType(userTypeRepository.findByType(UserTypeEnum.STUDENT.name()));

		// give persisted entity to the student Object
		student.setUser(userRepository.save(user));// FIXME instead, do we need to call to Service method?
													// But the Service method needs to get DTO. Or are there
													// any other approaches to persist the User?

		student.setCreateDate(now);
		student.setUpdateDate(now);

		Student savedStudent = studentRepository.save(student);
		log.info("The student was successfully added: {}", savedStudent);

		return modelMapper.map(savedStudent, StudentDto.class);
	}

	public StudentDto updateStudent(StudentDto studentDto) {
		if (studentDto.getId() == null) {
			throw new IllegalArgumentException("Student ID cannot be null");
		}
		Student student = modelMapper.map(studentDto, Student.class);
		if (!studentRepository.existsById(student.getId()))
			throw new EntityNotFoundException("Student not found!");
		LocalDateTime now = LocalDateTime.now();
		student.setUpdateDate(now);
		Student updatedStudent;
		try {
			updatedStudent = studentRepository.save(student);
			log.info("Student updated: {}", updatedStudent);
		} catch (Exception e) {
			log.error("Error occurred while updating student: {}", e.getMessage());
			throw e;
		}
		return modelMapper.map(updatedStudent, StudentDto.class);
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

	public Page<StudentDto> getByNameSurnameStudentNo(Pageable pageable, String keyword) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting students by name, surname or studentNo: {} with pageable: {}", keyword, pageable);
			Page<Student> students = studentRepository.findByNameOrSurnameOrStudentNo(keyword, keyword, keyword, pageable);
			Page<StudentDto> studentDtos = students.map(student -> modelMapper.map(student, StudentDto.class));
			return studentDtos;
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
		UserType userType = userTypeService.getUserTypeId(UserTypeEnum.STUDENT);

		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setUserType(userType);
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
