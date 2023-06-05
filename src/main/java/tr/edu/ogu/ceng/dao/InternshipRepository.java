package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.model.Internship;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
	Page<Internship> findAllByStudentId(Long studentId, Pageable pageable);

	Page<Internship> findAllByCompanyId(Long companyId, Pageable pageable);

	Page<Internship> findAllByFacultySupervisorId(Long faculty_supervisor_id, Pageable pageable);

	long countByStatus(InternshipStatus status);

	@Query("SELECT COUNT(i) FROM Internship i WHERE i.student.id = ?1")
	long countByStudentId(Long studentId);
}
