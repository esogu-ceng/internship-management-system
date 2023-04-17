package tr.edu.ogu.ceng.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	public Page<Company> findByNameContainingIgnoreCase(String searchString, Pageable pageable);

}
