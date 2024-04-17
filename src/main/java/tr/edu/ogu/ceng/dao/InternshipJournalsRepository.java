package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.InternshipJournal;

public interface InternshipJournalsRepository extends JpaRepository<InternshipJournal, Long> {

	Page<InternshipJournal> findAllJournalByInternshipId(Long internshipId, Pageable pageable);

}


