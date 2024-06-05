package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.ceng.model.InternshipApplication;

import java.util.List;

public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, Long> {
    List<InternshipApplication> findByStudentId(Long studentId);
}
