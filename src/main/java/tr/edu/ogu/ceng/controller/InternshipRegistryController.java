package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.InternshipRegistryDto;
import tr.edu.ogu.ceng.model.InternshipRegistry;
import tr.edu.ogu.ceng.service.InternshipRegistryService;

@RestController
@RequestMapping("/api/internshipregistry")
public class InternshipRegistryController {

	@Autowired
	private InternshipRegistryService internshipRegistryService;

	@DeleteMapping("/{id}")
	public boolean deleteInternshipRegistry(@PathVariable(name = "id") long id) {
		return internshipRegistryService.deleteInternshipRegistry(id);
	}

	@PutMapping
	public ResponseEntity<InternshipRegistryDto> updateInternshipRegistry(@RequestBody InternshipRegistryDto internshipRegistryDto) {
		InternshipRegistryDto updatedInternshipRegistry = internshipRegistryService.updateInternshipRegistry(internshipRegistryDto);
		return ResponseEntity.ok(updatedInternshipRegistry);
	}

	@PostMapping
	public ResponseEntity<InternshipRegistryDto> addInternshipRegistries(@RequestBody InternshipRegistryDto internshipRegistriesDto) {
		InternshipRegistryDto addedInternshipRegistry = internshipRegistryService.addInternshipRegistry(internshipRegistriesDto);
		return ResponseEntity.ok(addedInternshipRegistry);
	}

}
