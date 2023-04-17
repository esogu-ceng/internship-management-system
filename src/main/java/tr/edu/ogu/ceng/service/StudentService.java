package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dao.UserTypeRepository;
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

	public Student getStudent(long id) {
		try {
			Student student = studentRepository.findById(id).orElse(null);
			if (student == null) {
				log.warn("Girdiğiniz id'ye ait öğrenci bulunmamaktadır.");
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			log.info("{} id'ye sahip öğrenci {} numaralı ,{} {}. ", student.getId(), student.getStudentNo(),
					student.getName(), student.getSurname());
			return student;
		} catch (EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public Page<Student> getAllStudents(Pageable pageable) {
		if (studentRepository.findAll() == null) {
			log.warn("Öğrenci listesi boş.");
			return null;
		}
		log.info("Öğrenci listesi başarıyla getirildi.");
		return studentRepository.findAll(pageable);
	}

	public Student addStudent(Student student) {
		log.info("Öğrenci başarılı bir şekilde eklendi.");
		return studentRepository.save(student);
	}

	public Student updateStudent(Student student) {
		if (!studentRepository.existsById(student.getId())) {
			log.warn("{} id'ye sahip öğrenci bulunmamaktadır.", student.getId());
			throw new EntityNotFoundException("Student not found!");
		}
		log.info("Öğrencinin bilgileri güncellendi.");
		student.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		return studentRepository.save(student);
	}

	@Transactional
	public boolean deleteStudent(long id) {
		if (!studentRepository.existsById(id)) {
			log.warn("Girdiğiniz id'ye sahip öğrenci bulunmadığı için silme işlemi gerçekleştirilemedi.");
			return false;
		}
		studentRepository.deleteById(id);
		log.info("Girdiğiniz id'ye sahip öğrenci silindi.");
		return true;
	}

	public Page<Student> getStudentsByName(Pageable pageable, String name) {
		// TODO Exception and Logging
		return studentRepository.findByName(name, pageable);
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
