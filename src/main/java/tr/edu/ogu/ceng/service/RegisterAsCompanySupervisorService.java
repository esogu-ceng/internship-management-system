package tr.edu.ogu.ceng.service;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dao.RegisterAsCompanySupervisorRepository;
import tr.edu.ogu.ceng.dto.RegisterAsCompanySupervisorDto;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.User;

@Service
@AllArgsConstructor
public class RegisterAsCompanySupervisorService{

	private final RegisterAsCompanySupervisorRepository repository;
	
	public RegisterAsCompanySupervisorDto register(RegisterAsCompanySupervisorDto request) {

		checkIfPasswordsMatchingValidation(request);
		CompanySupervisor companySupervisor=new CompanySupervisor();
		User user = new User();
		Company company = new Company();
		companySupervisor.setUser(user);
		companySupervisor.setCompany(company);
		companySupervisor.setName(request.getName());
		companySupervisor.setSurname(request.getSurname());
		companySupervisor.setPhoneNumber(request.getPhoneNumber());
		companySupervisor.getUser().setId(request.getUserId());
		companySupervisor.getUser().setPassword(request.getPassword());
		companySupervisor.getCompany().setId(request.getCompanyId());
		companySupervisor.setUpdateDate(LocalDateTime.now());
		companySupervisor.setCreateDate(LocalDateTime.now());
		CompanySupervisor registeredCompanySupervisor = repository.save(companySupervisor);
		
		RegisterAsCompanySupervisorDto response=new RegisterAsCompanySupervisorDto();
		response.setId(registeredCompanySupervisor.getId());
		response.setName(registeredCompanySupervisor.getName());
		response.setSurname(registeredCompanySupervisor.getSurname());
		response.setPhoneNumber(registeredCompanySupervisor.getPhoneNumber());
		response.setUserId(registeredCompanySupervisor.getUser().getId());
		response.setPassword(registeredCompanySupervisor.getUser().getPassword());
		response.setConfirmPassword(registeredCompanySupervisor.getUser().getPassword());
		response.setCompanyId(registeredCompanySupervisor.getCompany().getId());
		response.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		response.setCreateDate(new Timestamp(System.currentTimeMillis()));
		
		return response;
	}

	private void checkIfPasswordsMatchingValidation(RegisterAsCompanySupervisorDto request) {
		if(!request.getPassword().toString().equals(request.getConfirmPassword().toString())) 
			throw new RuntimeException("Şifreler uyuşmuyor!");
	}

}