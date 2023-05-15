package tr.edu.ogu.ceng.service;


import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.RegisterAsCompanySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.RegisterAsCompanySupervisorResponseDto;
import tr.edu.ogu.ceng.enums.UserType;

@Service
@AllArgsConstructor
public class RegisterAsCompanySupervisorService{

	private final CompanySupervisorService companySupervisorService;
	private final UserService userService;
	private final ModelMapper mapper;
	
	public RegisterAsCompanySupervisorResponseDto register(RegisterAsCompanySupervisorRequestDto request) {

		checkIfPasswordsMatchingValidation(request);
		
		UserDto userDto = mapper.map(request, UserDto.class);
		userDto.setCreateDate(LocalDateTime.now());
		userDto.setUpdateDate(LocalDateTime.now());
		userDto.setUserType(UserType.COMPANYSUPERVISOR);
		UserDto createdUserDto = userService.saveUser(userDto);
		
		CompanySupervisorDto companySupervisorDto = mapper.map(request, CompanySupervisorDto.class);
		companySupervisorDto.setUser(createdUserDto);
		companySupervisorDto.setCreateDate(LocalDateTime.now());
		companySupervisorDto.setUpdateDate(LocalDateTime.now());
		CompanySupervisorDto createdCompanySupervisorDto = companySupervisorService.add(companySupervisorDto);
		
		RegisterAsCompanySupervisorResponseDto response = mapper.map(createdCompanySupervisorDto, RegisterAsCompanySupervisorResponseDto.class);
		response.setUsername(createdUserDto.getUsername());
		response.setEmail(createdUserDto.getEmail());
		response.setUserType(createdUserDto.getUserType());
		
		return response;
	}

	private void checkIfPasswordsMatchingValidation(RegisterAsCompanySupervisorRequestDto request) {
		if(!request.getPassword().toString().equals(request.getConfirmPassword().toString())) 
			throw new RuntimeException("Şifreler uyuşmuyor!");
	}

}