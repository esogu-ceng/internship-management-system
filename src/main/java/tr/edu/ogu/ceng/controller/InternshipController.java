package tr.edu.ogu.ceng.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.Internship;
import tr.edu.ogu.ceng.service.InternshipService;


@RestController
@RequestMapping("/api/internships")
public class InternshipController {
	@Autowired
	InternshipService internshipService;
	
	@PutMapping("/{id}")
	public Internship updateInternship(@RequestBody Internship internship, @PathVariable(name="id") Long id) {
		return internshipService.updateInternship(internship, id);
	}
	

}
