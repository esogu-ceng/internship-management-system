package tr.edu.ogu.ceng.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Student;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudent() {
		return studentRepository.findAll();
	}

	public Student getStudent(int id) {
		return studentRepository.findById(id).orElse(null);
	}

	public int addStudent(Student student) {
		return studentRepository.save(student).getId();
	}

}
