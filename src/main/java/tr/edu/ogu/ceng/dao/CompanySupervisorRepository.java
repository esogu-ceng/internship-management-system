package tr.edu.ogu.ceng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.CompanySupervisor;

public interface CompanySupervisorRepository extends JpaRepository<CompanySupervisor, Long> {
	boolean existsByUserId(Long userId);

	List<CompanySupervisor> findAllByCompanyId(Long companyId);

	CompanySupervisor findByUserId(Long userId);
}
