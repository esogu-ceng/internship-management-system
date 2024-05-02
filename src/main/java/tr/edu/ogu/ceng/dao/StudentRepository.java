package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.edu.ogu.ceng.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Page<Student> findByNameOrSurnameOrStudentNo(String keyword1, String keyword2, String keyword3, Pageable pageable);
	Page<Student> findAllByFacultyId(Long faculty_id, Pageable pageable);

	@Query("SELECT s FROM Student s JOIN s.user u WHERE u.id = :id")
	Student findByUserId(Long id);
	

	boolean existsByTckn(String tcno);

	boolean existsByStudentNo(String studentno);

	Optional<Student> findByStudentNo(String studentNo);
}
