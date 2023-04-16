package tr.edu.ogu.ceng.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.dao.RegisterAsCompanySupervisorRepository;
import tr.edu.ogu.ceng.dto.RegisterAsCompanySupervisorDto;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.User;

@Slf4j
@Service
@AllArgsConstructor
public class RegisterAsCompanySupervisorService {

	private final RegisterAsCompanySupervisorRepository repository;

	public RegisterAsCompanySupervisorDto register(RegisterAsCompanySupervisorDto request) {

		checkIfPasswordsMatchingValidation(request);
		CompanySupervisor companySupervisor = new CompanySupervisor();
		User user = new User();
		Company company = new Company();
		companySupervisor.setUser(user);
		companySupervisor.setCompany(company);
		companySupervisor.setId(0);
		companySupervisor.setName(request.getName());
		companySupervisor.setSurname(request.getSurname());
		companySupervisor.setPhoneNumber(request.getPhoneNumber());
		companySupervisor.getUser().setId(request.getUserId());
		companySupervisor.getUser().setPassword(request.getPassword());
		companySupervisor.getCompany().setId(request.getCompanyId());
		companySupervisor.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		companySupervisor.setCreateDate(new Timestamp(System.currentTimeMillis()));
		log.info("New company supervisor registered: {}", companySupervisor.getName());
		repository.save(companySupervisor);

		RegisterAsCompanySupervisorDto response = new RegisterAsCompanySupervisorDto();
		response.setName(companySupervisor.getName());
		response.setSurname(companySupervisor.getSurname());
		response.setPhoneNumber(companySupervisor.getPhoneNumber());
		response.setUserId(companySupervisor.getUser().getId());
		response.setPassword(companySupervisor.getUser().getPassword());
		response.setConfirmPassword(companySupervisor.getUser().getPassword());
		response.setCompanyId(companySupervisor.getCompany().getId());
		response.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		response.setCreateDate(new Timestamp(System.currentTimeMillis()));
		return response;
	}

	private void checkIfPasswordsMatchingValidation(RegisterAsCompanySupervisorDto request) {
		if (!request.getPassword().toString().equals(request.getConfirmPassword().toString()))
			log.warn("This password and confirm password do not match.");
		throw new RuntimeException("Şifreler uyuşmuyor!");
	}

}