package tr.edu.ogu.ceng.service;


import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Student;

@Service
@AllArgsConstructor
public class StudentService {

	private final StudentRepository studentRepository;

    public Student getStudent(long id) throws EntityNotFoundException {
    	Student student = studentRepository.findById(id).orElse(null);
		if (student == null) {
    		throw new EntityNotFoundException();
    	}
        return student;
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
