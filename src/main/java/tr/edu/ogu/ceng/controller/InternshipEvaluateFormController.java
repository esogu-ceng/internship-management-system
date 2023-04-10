package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;
import tr.edu.ogu.ceng.service.InternshipEvaluateFormService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/internship-evaluate-forms")
public class InternshipEvaluateFormController {

	@Autowired
	private final InternshipEvaluateFormService internshipEvaluateFormService;

	@PostMapping
	public ResponseEntity<InternshipEvaluateForm> createInternshipEvaluateForm(@RequestBody InternshipEvaluateForm internshipEvaluateForm) {
		InternshipEvaluateForm createdForm = internshipEvaluateFormService.createInternshipEvaluateForm(internshipEvaluateForm);
		return new ResponseEntity<>(createdForm, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InternshipEvaluateForm> getInternshipEvaluateFormById(@PathVariable Long id) {
		InternshipEvaluateForm form = internshipEvaluateFormService.getInternshipEvaluateFormById(id);
		return new ResponseEntity<>(form, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<InternshipEvaluateForm>> getAllInternshipEvaluateForms() {
		List<InternshipEvaluateForm> forms = internshipEvaluateFormService.getAllInternshipEvaluateForms();
		return new ResponseEntity<>(forms, HttpStatus.OK);
	}
}
