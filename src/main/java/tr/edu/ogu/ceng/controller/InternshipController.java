package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.service.InternshipService;

@RestController
@RequestMapping("/api/v1/internship")
public class InternshipController {
	@Autowired
	InternshipService internshipService;
	@PostMapping("")
	public ResponseEntity<Internship> AddInternship(@RequestBody Internship internship) {
		return ResponseEntity.ok(internshipService.AddInternship(internship));
	}
}
