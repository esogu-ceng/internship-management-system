package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
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
	private final UserTypeService userTypeService;

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
				log.warn("Öğrenci listesi boş.");
				return null;
			}
			Page<StudentDto> studentDtos = students.map(student -> modelMapper.map(student, StudentDto.class));
			return studentDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting students: {}", e.getMessage());
			throw e;
		}
	}

	public Student addStudent(Student student) {
		Student savedStudent = studentRepository.save(student);
		log.info("Student added successfully: {}", savedStudent);
		return savedStudent;
	}

	public Student updateStudent(Student student) {
		if (!studentRepository.existsById(student.getId())) {
			log.warn("{} id'ye sahip öğrenci bulunmamaktadır.", student.getId());
			throw new EntityNotFoundException("Student not found!");
		}
		log.info("The student information has been updated..");
		student.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		return studentRepository.save(student);
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

	public Page<StudentDto> getStudentsByName(Pageable pageable, String name) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			log.info("Getting students by name: {} with pageable: {}", name, pageable);
			Page<Student> students = studentRepository.findByName(name, pageable);
			Page<StudentDto> studentDtos = students.map(student -> modelMapper.map(student, StudentDto.class));
			return studentDtos;
		} catch (Exception e) {
			log.error("An error occurred while getting students by name: {}: {}", name, e.getMessage());
			throw e;
		}
	}

	// TODO
	public Student getStudentByUserId() {
		return null;
	}

	public StudentDto registerAsStudent(StudentDto request) {

		checkIfPasswordsMatchingValidation(request);

		Faculty faculty = new Faculty();

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
		student.setFaculty(faculty);

		student.getFaculty().setId(request.getFacultyId());
		student.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		student.setCreateDate(new Timestamp(System.currentTimeMillis()));
		studentRepository.save(student);
		log.info("Kayıt başarılı");

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
