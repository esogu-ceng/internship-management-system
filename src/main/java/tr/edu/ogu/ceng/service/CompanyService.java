package tr.edu.ogu.ceng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public Company updateCompany(Company company) {
		if (!companyRepository.existsById(company.getId()))
			throw new EntityNotFoundException("Company not found!");
		return companyRepository.save(company);
	}

	public Company getCompany(long id) throws EntityNotFoundException {
		Company company = companyRepository.findById(id).orElse(null);
		if (company == null) {
			throw new EntityNotFoundException();
		}
		return company;
	}


  

    public List<Company> searchCompanies(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("Name should be at least 3 characters long.");
        }
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

}