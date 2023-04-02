package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;
  
    public Company getCompany(long id) throws EntityNotFoundException {
    	Company company = companyRepository.findById(id).orElse(null);
    	if (company == null) throw new EntityNotFoundException();
		return company;
    }
}