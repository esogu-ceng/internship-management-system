package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.InternshipRegistry;

import java.util.List;

public interface CompanySupervisorRepository extends JpaRepository<CompanySupervisor,Long>{
	boolean existsByUserId(Long userId);
	List<CompanySupervisor> findAllByCompanyId(Long companyId);
}
