package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.edu.ogu.ceng.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	@Query("SELECT s FROM Student s")
	Page<Student> findAllStudent(Pageable pageable);

}
