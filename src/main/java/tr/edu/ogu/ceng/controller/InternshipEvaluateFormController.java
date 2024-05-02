package tr.edu.ogu.ceng.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.InternshipEvaluateFormDto;
import tr.edu.ogu.ceng.dto.requests.InternshipEvaluateFormRequestDto;
import tr.edu.ogu.ceng.model.InternshipEvaluateForm;
import tr.edu.ogu.ceng.service.InternshipEvaluateFormService;
import tr.edu.ogu.ceng.util.PageableUtil;

@AllArgsConstructor
@RestController
@RequestMapping("/api/internship-evaluate-forms")
public class InternshipEvaluateFormController {

	private InternshipEvaluateFormService internshipEvaluateFormService;

	@PostMapping("/save")
	public ResponseEntity<InternshipEvaluateFormDto> saveInternshipRegistry(@RequestBody InternshipEvaluateFormDto registryDto) {
		InternshipEvaluateFormDto savedForm = internshipEvaluateFormService.saveInternshipEvalForm(registryDto);
		return new ResponseEntity<>(savedForm, HttpStatus.CREATED);
	}

	@PostMapping("/upload")
	public ResponseEntity<InternshipEvaluateForm> formFileUpload(@RequestPart(required = false) MultipartFile file,
			@RequestPart("dto") InternshipEvaluateFormDto dto) {
		InternshipEvaluateForm newForm = internshipEvaluateFormService.formFileUpload(file, dto);
		return new ResponseEntity<>(newForm, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InternshipEvaluateForm> getInternshipEvaluateFormById(@PathVariable Long id) {
		InternshipEvaluateForm form = internshipEvaluateFormService.getInternshipEvaluateFormById(id);
		return new ResponseEntity<>(form, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<InternshipEvaluateForm> getAllInternshipEvaluateForms(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipEvaluateForm> forms = internshipEvaluateFormService.getAllInternshipEvaluateForms(pageable);
		return forms;
	}

	@GetMapping("/getByInternshipId/{internshipId}")
	public ResponseEntity<InternshipEvaluateForm> getByInternshipId(@PathVariable Long internshipId) {
		InternshipEvaluateForm form = internshipEvaluateFormService.getByInternshipId(internshipId);
		if (form != null) {
			return new ResponseEntity<>(form, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping()
	public ResponseEntity<InternshipEvaluateFormDto> addInternshipEvaluateForm(@RequestBody InternshipEvaluateFormRequestDto internshipEvaluateFormRequestDto) {
		return ResponseEntity.ok(internshipEvaluateFormService.addInternshipEvaluateForm(internshipEvaluateFormRequestDto));
	}

	@PutMapping()
	public ResponseEntity<InternshipEvaluateFormDto> updateInternship(@RequestBody InternshipEvaluateFormRequestDto internshipEvaluateFormRequestDto) {
		return ResponseEntity.ok(internshipEvaluateFormService.updateInternshipEvaluateForm(internshipEvaluateFormRequestDto));
	}

}
