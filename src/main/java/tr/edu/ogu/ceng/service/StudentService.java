package tr.edu.ogu.ceng.service;


import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Student;

@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;

	public Student getStudent(int id) {
		return studentRepository.findById(id).orElse(null);
	}

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	
	public boolean deleteStudent(long id) {
		
		if(!studentRepository.existsById(id)) 
			throw new EntityNotFoundException("Student Not Found!");
		
		studentRepository.deleteById(id);
		return true;
	}
  
}
