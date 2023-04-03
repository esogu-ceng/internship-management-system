package tr.edu.ogu.ceng.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;
    
    public Company updateCompany(Company company) {
    	if(!companyRepository.existsById(company.getId())) throw new EntityNotFoundException("Company not found!");
    	return companyRepository.save(company);
    }

}