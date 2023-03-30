package tr.edu.ogu.ceng.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;
    
    public Company updateCompany(Company company, long id) {
    	if(!companyRepository.existsById(id)) throw new EntityNotFoundException("Company not found!");
    	company.setId(id);
    	return companyRepository.save(company);
    }
    
    public List<Company> searchCompanies(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("Name should be at least 3 characters long.");
        }
        return companyRepository.findByNameContainingIgnoreCase(name);
    }


}