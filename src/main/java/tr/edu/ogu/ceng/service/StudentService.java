package tr.edu.ogu.ceng.service;


import java.sql.Timestamp;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
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
    		throw new EntityNotFoundException("Student Not Found!");
    	}
        return student;
    }

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}
	
    public Student updateStudent(Student student){
        if(!studentRepository.existsById(student.getId())) throw new EntityNotFoundException("Student not found!");
        Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
        student.setUpdateDate(localDateTime);
        return studentRepository.save(student);
    }
	
	public boolean deleteStudent(long id) {
		
		if(!studentRepository.existsById(id)) 
			throw new EntityNotFoundException("Student Not Found!");
		
		studentRepository.deleteById(id);
		return true;
	}
  
}
