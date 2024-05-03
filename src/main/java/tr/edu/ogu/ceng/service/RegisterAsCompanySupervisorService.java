package tr.edu.ogu.ceng.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.RegisterAsCompanySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.RegisterAsCompanySupervisorResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.CompanySupervisor;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.Exception.PasswordsNotMatchedException;
import tr.edu.ogu.ceng.service.Exception.UnconfirmedMailAddressException;

@Slf4j
@Service
@AllArgsConstructor
public class RegisterAsCompanySupervisorService {

	private final CompanySupervisorService companySupervisorService;
	private final CompanyService companyService;
	private final UserService userService;
	private final ModelMapper mapper;

	@Transactional
	public RegisterAsCompanySupervisorResponseDto register(RegisterAsCompanySupervisorRequestDto request) {

		checkIfPasswordsMatchingValidation(request);
		checkIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress(request);

		UserDto userDto = mapper.map(request, UserDto.class);
		userDto.setUserType(UserType.COMPANYSUPERVISOR);

		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(userDto, User.class);
		User createdUser = userService.addUser(user);

		CompanySupervisorDto companySupervisorDto = mapper.map(request, CompanySupervisorDto.class);
		CompanySupervisor companySupervisor = modelMapper.map(companySupervisorDto, CompanySupervisor.class);
		companySupervisor.setUser(createdUser);
		CompanySupervisor createdCompanySupervisor = companySupervisorService.add(companySupervisor);

		RegisterAsCompanySupervisorResponseDto response = mapper.map(createdCompanySupervisor,
				RegisterAsCompanySupervisorResponseDto.class);
		response.setEmail(createdUser.getEmail());
		response.setUserType(createdUser.getUserType());

		log.info("Company supervisor registered successfully with e-mail: {} and id: {}", createdUser.getEmail(),
				createdUser.getId());
		return response;
	}

	public void checkIfPasswordsMatchingValidation(RegisterAsCompanySupervisorRequestDto request) {
		if (!request.getPassword().toString().equals(request.getConfirmPassword().toString())){
			log.error("Passwords are not matching");
			throw new PasswordsNotMatchedException();
		}
		else {
			log.info("Passwords are matching");
		}
	}

	public void checkIfSupervisorEmailAddressMatchingConfirmedCompanyEmailAddress(RegisterAsCompanySupervisorRequestDto request) {
		UserDto userDto = mapper.map(request, UserDto.class);
		CompanyDto companyDto = companyService.getCompany(request.getCompany().getId());

		String supervisorMail = userDto.getEmail();
		String companyMail = companyDto.getEmail();

		String confirmedCompanyEmailAddress = companyMail.substring(companyMail.indexOf("@"));

		if (!supervisorMail.endsWith(confirmedCompanyEmailAddress)) {
			log.error("Supervisor's e-mail address does not match confirmed company's e-mail address!");
			throw new UnconfirmedMailAddressException();
		}
	}

}