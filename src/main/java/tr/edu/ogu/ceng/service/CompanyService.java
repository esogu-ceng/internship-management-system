package tr.edu.ogu.ceng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.dto.CompanyDto;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;

  
    public CompanyDto getCompany(long id) {
    	Company c = companyRepository.findById(id).orElse(null);
    	if (c == null) return null;
		return new CompanyDto(c.getId(), c.getName(), c.getAddress(), c.getPhoneNumber(), c.getFaxNumber(),c.getEmail(), c.getScope(), c.getDescription());
    }

}