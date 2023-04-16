package tr.edu.ogu.ceng.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.InvalidArgumentException;

@Slf4j
@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public Page<Company> getAllCompanies(Pageable pageable) {
		return companyRepository.findAll(pageable);
	}

	public Company updateCompany(Company company) {
		if (!companyRepository.existsById(company.getId())) {
			log.error("Company not found");
			throw new EntityNotFoundException("Company not found!");
		}
		
		log.info("Company updated successfully.");
		return companyRepository.save(company);
	}

	public Company getCompany(long id) throws EntityNotFoundException {
		Company company = companyRepository.findById(id).orElse(null);
		if (company == null) {
			log.error("Company not found with id {}", id);
			throw new EntityNotFoundException("Company not found!");
		}
		return company;
	}

	public Page<Company> searchCompanies(String name, Pageable pageable) {
		if (name.length() < 3) {
			log.error("Lenght of the company name is less than 3.");
			throw new InvalidArgumentException("Name should be at least 3 characters long.");
		}
		return companyRepository.findByNameContainingIgnoreCase(name, pageable);

	}

	public boolean deleteCompany(long id) {
		if (!companyRepository.existsById(id)) {
			log.info("Company not found with id {}", id);
			return false;
		}
			
		companyRepository.deleteById(id);
		log.info("Company deleted successfully.");
		return true;
	}

}