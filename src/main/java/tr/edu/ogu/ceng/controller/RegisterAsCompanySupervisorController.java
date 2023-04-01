package tr.edu.ogu.ceng.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.registerAsCompanySupervisor.RegisterAsCompanySupervisorRequest;
import tr.edu.ogu.ceng.dto.registerAsCompanySupervisor.RegisterAsCompanySupervisorResponse;
import tr.edu.ogu.ceng.service.abstracts.RegisterAsCompanySupervisorService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/register-as-company-supervisor")
public class RegisterAsCompanySupervisorController {

	@Autowired
	RegisterAsCompanySupervisorService registerAsCompanySupervisorService;
	
	@PostMapping("/register")
	@ResponseStatus(code=HttpStatus.CREATED)
	public RegisterAsCompanySupervisorResponse register(@RequestBody @Valid RegisterAsCompanySupervisorRequest request) {
		
		return this.registerAsCompanySupervisorService.register(request);
	}
}
