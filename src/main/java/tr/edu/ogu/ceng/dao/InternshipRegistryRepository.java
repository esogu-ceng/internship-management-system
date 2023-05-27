package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.InternshipRegistry;

public interface InternshipRegistryRepository extends JpaRepository<InternshipRegistry, Long> {

	Page<InternshipRegistry> findAllByInternshipId(Long internshipId, Pageable pageable);

}
