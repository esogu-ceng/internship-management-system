package tr.edu.ogu.ceng.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.InternshipDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.service.InternshipService;

@RestController
@RequestMapping("/api/internship")
public class InternshipController {
	@Autowired
	InternshipService internshipService;

	@PostMapping()
	public ResponseEntity<InternshipResponseDto> addInternship(@RequestBody InternshipRequestDto internshipDto) {
		return ResponseEntity.ok(internshipService.addInternship(internshipDto));
	}

	@PutMapping
	public ResponseEntity<InternshipDto> updateInternship(@RequestBody InternshipRequestDto internshipDto) {

		InternshipDto updatedInternship = internshipService.updateInternship(internshipDto);
		return ResponseEntity.ok(updatedInternship);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Internship>> getInternship(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(internshipService.getInternship(id));
	}

	@DeleteMapping("/{id}")
	public boolean deleteInternship(@PathVariable(name = "id") Long id) {
		return internshipService.deleteInternship(id);
	}

	@PutMapping("/approve/{id}")
	public Internship approveInternship(@PathVariable(name = "id") long id) {
		return internshipService.approveInternship(id);
	}
}
