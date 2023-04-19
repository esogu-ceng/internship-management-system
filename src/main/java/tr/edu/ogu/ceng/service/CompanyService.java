package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.InvalidArgumentException;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public Page<CompanyDto> getAllCompanies(Pageable pageable) {
		ModelMapper modelMapper = new ModelMapper();
		Page<Company> companies = companyRepository.findAll(pageable);
		Page<CompanyDto> companyDtos = companies.map(company -> modelMapper.map(company, CompanyDto.class));
		return companyDtos;

	}

	public CompanyDto updateCompany(CompanyDto companyDto) {
		try {
			if (!companyRepository.existsById(companyDto.getId())) {
				log.warn("There is no company with the entered ID.");
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			ModelMapper modelMapper = new ModelMapper();
			Company company = modelMapper.map(companyDto, Company.class);
			company = companyRepository.save(company);
			log.info("Company with ID {} has been updated: {}, {}", company.getId(), company.getName(), company.getDescription());
			return modelMapper.map(company, CompanyDto.class);
		} catch (EntityNotFoundException e) {
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public CompanyDto getCompany(long id) throws EntityNotFoundException {
		Company company = companyRepository.findById(id).orElse(null);
		if (company == null) {
			throw new EntityNotFoundException();
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(company, CompanyDto.class);
	}

	public Page<CompanyDto> searchCompanies(String name, Pageable pageable) {
		if (name.length() < 3) {
			throw new InvalidArgumentException("Name should be at least 3 characters long.");
		}
		ModelMapper modelMapper = new ModelMapper();
		Page<Company> companies = companyRepository.findByNameContainingIgnoreCase(name, pageable);
		Page<CompanyDto> companyDtos = companies.map(company -> modelMapper.map(company, CompanyDto.class));
		return companyDtos;
	}

	public CompanyDto addCompany(CompanyDto companyDto) {
		ModelMapper modelMapper = new ModelMapper();
		Company company = modelMapper.map(companyDto, Company.class);
		Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
		company.setCreateDate(localDateTime);
		company.setUpdateDate(localDateTime);
		Company newCompany = companyRepository.save(company);
		if (newCompany == null) {
			log.error("An error occurred while adding the company.");
			throw new EntityNotFoundException();
		}
		log.info("The company has been added successfully.");
		return modelMapper.map(newCompany, CompanyDto.class);
	}

	public boolean deleteCompany(long id) {
		if (!companyRepository.existsById(id))
			return false;
		companyRepository.deleteById(id);
		return true;
	}

}