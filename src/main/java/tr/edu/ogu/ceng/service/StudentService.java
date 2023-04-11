package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<Student> getAllStudents(Pageable pageable) {
		if (studentRepository.findAll() == null)
			return null;
		return studentRepository.findAll(pageable);
	}

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	public Student updateStudent(Student student) {
		if (!studentRepository.existsById(student.getId()))
			throw new EntityNotFoundException("Student not found!");
		student.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		return studentRepository.save(student);
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
