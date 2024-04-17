package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import tr.edu.ogu.ceng.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	public Page<Company> findByNameContainingIgnoreCase(String searchString, Pageable pageable);

	@Query("SELECT c FROM Company c INNER JOIN CompanySupervisor cs on c.id=cs.company.id WHERE cs.user.id = :userId")
	public Company findCompanyByCompanyUserId(Long userId);

}
