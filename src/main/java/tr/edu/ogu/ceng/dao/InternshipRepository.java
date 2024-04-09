package tr.edu.ogu.ceng.dao;

import java.time.LocalDate;
import java.util.List;

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

	@Query(value = "SELECT COUNT(DISTINCT student_id) FROM ims_internships", nativeQuery = true)
	long countDistinctStudents();
	
	@Query(value = "SELECT EXTRACT(YEAR FROM i.startDate) AS year, COUNT(*) AS count " +
             "FROM Internship i " +
             "GROUP BY EXTRACT(YEAR FROM i.startDate) " +
             "ORDER BY EXTRACT(YEAR FROM i.startDate)")
	List<Object[]> countInternshipsByYear();
	
	@Query(value = "SELECT EXTRACT(MONTH FROM i.startDate) AS month, COUNT(*) AS count " +
            "FROM Internship i " +
            "GROUP BY EXTRACT(MONTH FROM i.startDate) " +
            "ORDER BY EXTRACT(MONTH FROM i.startDate)")
	List<Object[]> countInternshipsByMonth();

	@Query("SELECT i FROM Internship i WHERE i.status = :status " +
			"AND i.startDate = :start_date AND i.status = :status")
	List<Internship> findByStatusAndStartDateAndStatus(
			InternshipStatus status, LocalDate startDate);
}
