package tr.edu.ogu.ceng.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public boolean deleteStudent(long id) {
		
		if(!studentRepository.existsById(id)) 
			throw new EntityNotFoundException("Student Not Found!");
		
		studentRepository.deleteById(id);
		return true;
	}
}
