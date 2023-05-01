package tr.edu.ogu.ceng.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.FacultySupervisorDto;
import tr.edu.ogu.ceng.service.FacultySupervisorService;

@RestController
@RequestMapping("/api/facultySupervisor")

public class FacultySupervisorController {

	@Autowired
	private FacultySupervisorService facultySupervisorService;

	@PostMapping("/saveFacultysupervisor")
	public ResponseEntity<FacultySupervisorDto> addFacultySupervisor(@RequestBody @Valid FacultySupervisorDto facultySupervisor) {
		FacultySupervisorDto facultySupervisor1 = facultySupervisorService.addFacultySupervisor(facultySupervisor);
		return new ResponseEntity<>(facultySupervisor1, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<FacultySupervisorDto> updateFacultySupervisor(@RequestBody FacultySupervisorDto facultySupervisor) {
		FacultySupervisorDto updatedFacultySupervisor = facultySupervisorService.updateFacultySupervisor(facultySupervisor);
		return ResponseEntity.ok(updatedFacultySupervisor);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FacultySupervisorDto> getFacultySupervisor(@PathVariable(name = "id") long id) {
		FacultySupervisorDto facultySupervisorDto = facultySupervisorService.getFacultySupervisor(id);
		return ResponseEntity.ok(facultySupervisorDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFacultySupervisor(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(facultySupervisorService.deleteFacultySupervisor(id));
	}
}
