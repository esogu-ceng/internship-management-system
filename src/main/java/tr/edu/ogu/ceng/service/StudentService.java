package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Student;

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

	public List<Student> getAllStudents() {
		if (studentRepository.findAll() == null)
			return null;
		return studentRepository.findAll();
	}

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	public Student updateStudent(Student student) {

		Student dbStudent = studentRepository.findById(student.getId()).orElse(null);
		if (dbStudent == null)
			return null;
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		dbStudent = student;
		dbStudent.setUpdateDate(localDateTime);
		return studentRepository.save(dbStudent);
	}

	@Transactional
	public boolean deleteStudent(long id) {
		if (!studentRepository.existsById(id))
			return false;
		studentRepository.deleteById(id);
		return true;
	}

	// TODO
	public Student getStudentByUserId() {
		return null;
	}

}
