package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.CompanySupervisor;

public interface CompanySupervisorRepository extends JpaRepository<CompanySupervisor,Long>{
	boolean existsByUserId(Long userId);
}
