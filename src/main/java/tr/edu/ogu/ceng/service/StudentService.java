package tr.edu.ogu.ceng.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.model.Student;

@Service
public class StudentService {
	@Autowired
    private StudentRepository studentRepository;

    public Student getStudent(long id) {
    	return studentRepository.findById(id).orElse(null);
    }
}
