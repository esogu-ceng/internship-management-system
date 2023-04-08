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
	public ResponseEntity<InternshipRegistry> updateInternshipRegistry(@RequestBody InternshipRegistry internshipRegistry) {
		return ResponseEntity.ok(internshipRegistryService.updateInternshipRegistry(internshipRegistry));
	}

	@PostMapping
	public ResponseEntity<InternshipRegistry> addInternshipRegistries(@RequestBody InternshipRegistry internshipRegistries) {
		return ResponseEntity.ok(internshipRegistryService.addInternshipRegistry(internshipRegistries));
	}

}
