package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.InternshipEvaluateForm;

public interface InternshipEvaluateFormRepository extends JpaRepository<InternshipEvaluateForm, Long> {
	InternshipEvaluateForm findByInternshipId(Long internshipId);
}
