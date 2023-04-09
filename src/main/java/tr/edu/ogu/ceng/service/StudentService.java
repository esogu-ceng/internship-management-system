package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;

@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;

	public Student getStudent(long id) {
		try {
			Student student = studentRepository.findById(id).orElse(null);
			if (student == null) {
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			return student;
		} catch (EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	public Student updateStudent(Student student) {
		if (!studentRepository.existsById(student.getId()))
			throw new EntityNotFoundException("Student not found!");
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		student.setUpdateDate(localDateTime);
		return studentRepository.save(student);
	}

	public boolean deleteStudent(long id) {

		if (!studentRepository.existsById(id))
			throw new EntityNotFoundException("Student Not Found!");

		studentRepository.deleteById(id);
		return true;
	}
	
	public StudentDto registerAsStudent(StudentDto request) {
		checkIfPasswordsMatchingValidation(request);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		Student student = new Student();
		Faculty faculty = new Faculty();
		User user = new User();
		student.setUser(user);
		student.setFaculty(faculty);
		student.setId(0);
		student.setName(request.getName());
		student.setSurname(request.getSurname());
		student.setTckn(request.getTckn());
		student.setStudentNo(request.getStudentNo());
		student.setGrade(request.getGrade());
		student.setPhoneNumber(request.getPhoneNumber());
		student.setProvince(request.getProvince());
		student.setSubprovince(request.getSubprovince());
		student.setZipCode(request.getZipCode());
		student.setMotherName(request.getMotherName());
		student.setFatherName(request.getFatherName());
		student.setBirthPlace(request.getBirthPlace());
		student.getUser().setId(request.getUserId());
		student.getUser().setPassword(request.getPassword());
		student.getFaculty().setId(request.getFacultyId());
		student.setBirthDate(Timestamp.valueOf(request.getBirthDate()));
		student.setIdCardSerialNo(request.getIdCardSerialNo());
		student.setIdRegisterProvince(request.getIdRegisterProvince());
		student.setIdRegisterSubprovince(request.getIdRegisterSubprovince());
		student.setIdRegisterStreetVillage(request.getIdRegisterStreetVillage());
		student.setIdRegisterVolumeNo(request.getIdRegisterVolumeNo());
		student.setIdRegisterFamilySerialNo(request.getIdRegisterFamilySerialNo());
		student.setIdRegisterSerialNo(request.getIdRegisterSerialNo());
		student.setIdRegistryOffice(request.getIdRegistryOffice());
		student.setIdRegistryReason(request.getIdRegistryReason());
		student.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		student.setCreateDate(new Timestamp(System.currentTimeMillis()));
		studentRepository.save(student);

		StudentDto response = new StudentDto();
		response.setName(student.getName());
		response.setSurname(student.getSurname());
		response.setTckn(student.getTckn());
		response.setStudentNo(student.getStudentNo());
		response.setGrade(student.getGrade());
		response.setPhoneNumber(student.getPhoneNumber());
		response.setProvince(student.getProvince());
		response.setSubprovince(student.getSubprovince());
		response.setZipCode(student.getZipCode());
		response.setMotherName(student.getMotherName());
		response.setFatherName(student.getFatherName());
		response.setBirthPlace(student.getBirthPlace());
		response.setBirthDate(formatter.format(student.getBirthDate()));
		response.setIdCardSerialNo(student.getIdCardSerialNo());
		response.setIdRegisterProvince(student.getIdRegisterProvince());
		response.setIdRegisterSubprovince(student.getIdRegisterSubprovince());
		response.setIdRegisterStreetVillage(student.getIdRegisterStreetVillage());
		response.setIdRegisterVolumeNo(student.getIdRegisterVolumeNo());
		response.setIdRegisterFamilySerialNo(student.getIdRegisterFamilySerialNo());
		response.setIdRegisterSerialNo(student.getIdRegisterSerialNo());
		response.setIdRegistryOffice(student.getIdRegistryOffice());
		response.setIdRegistryReason(student.getIdRegistryReason());

		return response;
	}
	
	// This method checks if the entered password and confirmed password are the
	// same. If not, it throws a runtime exception.
	private void checkIfPasswordsMatchingValidation(StudentDto request) {
		if (!request.getPassword().equals(request.getConfirmPassword()))
			throw new RuntimeException("Passwords do not match!");
	}
	
}
