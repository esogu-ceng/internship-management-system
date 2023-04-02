package tr.edu.ogu.ceng.dao;

import tr.edu.ogu.ceng.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
}
