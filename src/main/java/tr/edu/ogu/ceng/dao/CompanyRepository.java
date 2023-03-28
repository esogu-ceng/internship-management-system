package tr.edu.ogu.ceng.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import  tr.edu.ogu.ceng.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	
}