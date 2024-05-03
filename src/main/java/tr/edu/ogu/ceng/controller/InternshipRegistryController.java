package tr.edu.ogu.ceng.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.requests.InternshipRegistryRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipRegistryResponseDto;
import tr.edu.ogu.ceng.service.InternshipRegistryService;
import tr.edu.ogu.ceng.util.PageableUtil;

@AllArgsConstructor
@RestController
@RequestMapping("/api/internshipregistry")
public class InternshipRegistryController {

	private InternshipRegistryService internshipRegistryService;

	@GetMapping("/getInternshipRegistiriesById")
	public Page<InternshipRegistryResponseDto> getAllInternshipRegistiries(
			@RequestParam Long internshipId,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipRegistryResponseDto> internshipRegistries = internshipRegistryService
				.getAllInternshipRegistiries(internshipId, pageable);
		return internshipRegistries;
	}

	@DeleteMapping("/{id}")
	public boolean deleteInternshipRegistry(@PathVariable(name = "id") long id) {
		return internshipRegistryService.deleteInternshipRegistry(id);
	}

	@PutMapping
	public ResponseEntity<InternshipRegistryResponseDto> updateInternshipRegistry(
			@RequestBody InternshipRegistryRequestDto requestDto) {
		InternshipRegistryResponseDto updatedInternshipRegistry = internshipRegistryService
				.updateInternshipRegistry(requestDto);
		return ResponseEntity.ok(updatedInternshipRegistry);
	}

	@PostMapping
	public ResponseEntity<InternshipRegistryResponseDto> addInternshipRegistries(
			@RequestBody InternshipRegistryRequestDto internshipRegistriesDto) {
		InternshipRegistryResponseDto addedInternshipRegistry = internshipRegistryService
				.addInternshipRegistry(internshipRegistriesDto);
		return ResponseEntity.ok(addedInternshipRegistry);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InternshipRegistryResponseDto> getInternship(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(internshipRegistryService.getInternshipRegistry(id));
	}

}
