package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.ogu.ceng.model.InternshipDocument;

public interface InternshipDocumentsRepository extends JpaRepository<InternshipDocument, Long> {

	Page<InternshipDocument> findAllDocumentByInternshipId(Long internshipId, Pageable pageable);

}
