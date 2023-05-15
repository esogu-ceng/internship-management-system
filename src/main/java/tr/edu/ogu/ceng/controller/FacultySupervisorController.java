package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.service.FacultySupervisorService;

@RestController
@RequestMapping("/api/facultySupervisor")

public class FacultySupervisorController {

	@Autowired
	private FacultySupervisorService facultySupervisorService;

	@PostMapping("/saveFacultysupervisor")
	public FacultySupervisorResponseDto addFacultySupervisor(
			@RequestBody @Validated FacultySupervisorRequestDto facultySupervisorRequestDto) {
		return facultySupervisorService.addFacultySupervisor(facultySupervisorRequestDto);
	}

	@PutMapping
	public ResponseEntity<FacultySupervisorResponseDto> updateFacultySupervisor(
			@RequestBody FacultySupervisorRequestDto facultySupervisorRequestDto) {
		FacultySupervisorResponseDto updatedFacultySupervisor = facultySupervisorService
				.updateFacultySupervisor(facultySupervisorRequestDto);
		return ResponseEntity.ok(updatedFacultySupervisor);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FacultySupervisorResponseDto> getFacultySupervisor(@PathVariable(name = "id") long id) {
		FacultySupervisorResponseDto facultySupervisorResponseDto = facultySupervisorService.getFacultySupervisor(id);
		return ResponseEntity.ok(facultySupervisorResponseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFacultySupervisor(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(facultySupervisorService.deleteFacultySupervisor(id));
	}
}