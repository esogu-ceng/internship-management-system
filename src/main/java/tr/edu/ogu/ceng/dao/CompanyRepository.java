package tr.edu.ogu.ceng.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.edu.ogu.ceng.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
<<<<<<< HEAD

=======
	public List<Company> findByNameContainingIgnoreCase(String searchString);
>>>>>>> 3214bb714e14965564d640148bf9da9f55beb429
}
