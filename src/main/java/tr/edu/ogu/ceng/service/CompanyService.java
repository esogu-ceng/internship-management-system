package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.InvalidArgumentException;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public Page<Company> getAllCompanies(Pageable pageable) {
		log.info("Getting companies with pageable{}", pageable);
		return companyRepository.findAll(pageable);
	}

	public Company updateCompany(Company company) {
		if (!companyRepository.existsById(company.getId())) {
			log.warn("No company found with id {}.", company.getId());
			throw new EntityNotFoundException("Company not found!");
		}
		return companyRepository.save(company);
	}

	public Company getCompany(long id) throws EntityNotFoundException {
		Company company = companyRepository.findById(id).orElse(null);
		if (company == null) {
			log.warn("Company not found.");
			throw new EntityNotFoundException();
		}
		log.info("Company with id {} is retrieved successfully.", id);
		return company;
	}

	public Page<Company> searchCompanies(String name, Pageable pageable) {
		if (name.length() < 3) {
			log.warn("The search query should consist of at least three characters.");
			throw new InvalidArgumentException("Name should be at least 3 characters long.");
		}
		return companyRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	public Company addCompany(Company company) {
		Company newCompany = companyRepository.save(company);
		if (newCompany == null) {
			log.error("Şirket eklenirken bir hata oluştu.");
			throw new EntityNotFoundException("Şirket eklenirken bir hata oluştu.");
		}
		log.info("Şirket başarılı bir şekilde eklendi.");
		return newCompany;
	}

	public boolean deleteCompany(long id) {
		if (!companyRepository.existsById(id)) {
			log.warn("Deletion process could not be performed because no company was found with the given ID ");
			return false;
		}
		companyRepository.deleteById(id);
		log.info("Deletion process is completed successfully");
		return true;
	}

}