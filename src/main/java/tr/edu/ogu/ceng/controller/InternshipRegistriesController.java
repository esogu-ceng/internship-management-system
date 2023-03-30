package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.ceng.model.InternshipRegistries;
import tr.edu.ogu.ceng.service.InternshipRegistriesService;

@RestController
@RequestMapping("/api/internshipregistry")
public class InternshipRegistriesController {

	@Autowired
	private InternshipRegistriesService internshipRegistriesService;
	
	@PutMapping
	public ResponseEntity<InternshipRegistries> updateInternshipRegistries(@RequestBody InternshipRegistries internshipRegistries) {
		return ResponseEntity.ok(internshipRegistriesService.updateInternshipRegistries(internshipRegistries));
	}
	
}
