package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Student;

@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;

	public Student getStudent(long id) {
		return studentRepository.findById(id).orElse(null);
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

	public Page<Student> getStudents(@RequestParam Integer pageSize, @RequestParam Integer page) {

		Pageable pageable = PageRequest.of(page, pageSize);
		return studentRepository.findAll(pageable);
	}
}
