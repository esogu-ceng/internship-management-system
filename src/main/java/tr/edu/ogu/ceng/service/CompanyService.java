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
		return companyRepository.findAll(pageable);
	}

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

	public Page<Company> searchCompanies(String name, Pageable pageable) {
		if (name.length() < 3) {
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
		if (!companyRepository.existsById(id))
			return false;
		companyRepository.deleteById(id);
		return true;
	}

}