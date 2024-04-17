package tr.edu.ogu.ceng.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.InternshipJournal;

public interface InternshipJournalsRepository extends JpaRepository<InternshipJournal, Long> {

	public List<InternshipJournal> findAllJournalByInternshipId(Long internshipId);

}


