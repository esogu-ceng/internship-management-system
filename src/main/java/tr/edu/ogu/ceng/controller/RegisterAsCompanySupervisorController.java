package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.requests.RegisterAsCompanySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.RegisterAsCompanySupervisorResponseDto;
import tr.edu.ogu.ceng.service.RegisterAsCompanySupervisorService;

@AllArgsConstructor
@RestController
@RequestMapping("/public/api/registerascompanysupervisor")
public class RegisterAsCompanySupervisorController {

	@Autowired
	private final RegisterAsCompanySupervisorService service;
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public RegisterAsCompanySupervisorResponseDto register(@RequestBody RegisterAsCompanySupervisorRequestDto request) {
		
		return service.register(request);
	}
}
