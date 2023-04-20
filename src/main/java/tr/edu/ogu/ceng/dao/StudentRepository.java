package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Page<Student> findByName(String name, Pageable pageable);

	Student findByTckn(String tcNo);

	Student findByStudentNo(String studentNo);

}
