package tr.edu.ogu.ceng.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.InvalidArgumentException;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyService {

	private CompanyRepository companyRepository;

	private MessageResource messageResource;

	public Page<CompanyDto> getAllCompanies(Pageable pageable) {
		ModelMapper modelMapper = new ModelMapper();
		Page<Company> companies = companyRepository.findAll(pageable);
		Page<CompanyDto> companyDtos = companies.map(company -> modelMapper.map(company, CompanyDto.class));

		log.info("Pageable companies fetched page number: {}", pageable.getPageNumber());
		return companyDtos;
	}

	public CompanyDto updateCompany(CompanyDto companyDto) {
		try {
			if (!companyRepository.existsById(companyDto.getId())) {

				log.warn("Company not found with id {}", companyDto.getId());
				throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
			}
			ModelMapper modelMapper = new ModelMapper();
			Company company = modelMapper.map(companyDto, Company.class);
			LocalDateTime dateTime = LocalDateTime.now();
			company.setUpdateDate(dateTime);
			company.setCreateDate(companyRepository.getById(companyDto.getId()).getCreateDate());
			company = companyRepository.save(company);
			log.info(messageResource.getMessage("company.service.info.company.updated", company.getId(), company.getName(),
					company.getDescription()));
			return modelMapper.map(company, CompanyDto.class);
		} catch (EntityNotFoundException e) {
			log.error("Failed to update company. Error message: {}", e.getMessage());
			throw new tr.edu.ogu.ceng.service.Exception.EntityNotFoundException();
		}
	}

	public CompanyDto getCompany(long id) throws EntityNotFoundException {
		Company company = companyRepository.findById(id).orElse(null);
		if (company == null) {
			log.warn(messageResource.getMessage("company.service.warn.company.not.found", id));
			throw new EntityNotFoundException();
		}
		ModelMapper modelMapper = new ModelMapper();
		log.info("Company with ID {} fetched", id);
		return modelMapper.map(company, CompanyDto.class);
	}

	public Page<CompanyDto> searchCompanies(String name, Pageable pageable) {
		if (name.length() < 3) {

			log.warn("Length of company name is less than 3 characters long.");
			throw new InvalidArgumentException("Name should be at least 3 characters long.");
		}
		ModelMapper modelMapper = new ModelMapper();
		Page<Company> companies = companyRepository.findByNameContainingIgnoreCase(name, pageable);
		Page<CompanyDto> companyDtos = companies.map(company -> modelMapper.map(company, CompanyDto.class));
		log.info("Companies with name containing {} fetched", name);
		return companyDtos;
	}

	public CompanyDto addCompany(CompanyDto companyDto) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Company company = modelMapper.map(companyDto, Company.class);
			LocalDateTime dateTime = LocalDateTime.now();
			company.setCreateDate(dateTime);
			company.setUpdateDate(dateTime);
			companyRepository.save(company);

			log.info("Company with ID {} has been added: {}", company.getId(), company.getName());
			return modelMapper.map(company, CompanyDto.class);
		} catch (Exception e) {
			log.error(messageResource.getMessage("company.service.error.failed.to.add.company", e.getMessage()));
			throw e;
		}
	}

	public boolean deleteCompany(long id) {
		if (!companyRepository.existsById(id)) {
			log.warn(messageResource.getMessage("company.service.warn.company.not.found.with.id", id));
			return false;
		}

		companyRepository.deleteById(id);

		log.info("Company with ID {} has been deleted", id);

		return true;
	}

	public Long countCompanies() {
		return companyRepository.count();
	}

}
