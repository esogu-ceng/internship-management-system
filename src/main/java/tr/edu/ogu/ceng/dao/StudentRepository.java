package tr.edu.ogu.ceng.dao;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import  tr.edu.ogu.ceng.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
