package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseCompanyDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.service.InternshipService;
import tr.edu.ogu.ceng.util.PageableUtil;

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
	public ResponseEntity<InternshipResponseDto> updateInternship(@RequestBody InternshipRequestDto internshipDto) {

		return ResponseEntity.ok(internshipService.updateInternship(internshipDto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<InternshipResponseDto> getInternship(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(internshipService.getInternship(id));
	}

	// FIXME : bu method company controller içinde olmalı
	@GetMapping("company/{id}")
	public ResponseEntity<CompanyDto> getCompanyByInternshipId(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(internshipService.getCompanyByInternshipId(id));
	}

	@DeleteMapping("/{id}")
	public boolean deleteInternship(@PathVariable(name = "id") Long id) {
		return internshipService.deleteInternship(id);
	}

	@PutMapping("/approve/{id}")
	public InternshipStatus approveInternship(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.APPROVED);
	}

	@PutMapping("/reject/{id}")
	public InternshipStatus rejectInternship(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.REJECTED);
	}

	@GetMapping("/student/{id}")
	public Page<InternshipResponseDto> getAllInternshipsByStudentId(@PathVariable(name = "id") Long studentId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipResponseDto> internships = internshipService.getAllInternshipsByStudentId(studentId, pageable);
		return internships;
	}

	// FIXME : company/{id} olarak değiştirilecek!
	@GetMapping("/companyid/{id}")
	public Page<InternshipResponseDto> getAllInternshipsByCompanyId(@PathVariable(name = "id") Long companyId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipResponseDto> internships = internshipService.getAllInternshipsByCompanyId(companyId, pageable);
		return internships;
	}

	@GetMapping("/{id}/student")
	public StudentResponseDto getStudentByInternshipId(@PathVariable(name = "id") long id) {
		return internshipService.getStudentByInternshipId(id);
	}

	@GetMapping("/supervisor/{id}")
	public Page<InternshipResponseCompanyDto> getAllInternshipsByFacultySupervisorId(
			@PathVariable(name = "id") Long faculty_supervisor_id, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipResponseCompanyDto> internships = internshipService
				.getAllInternshipsByFacultySupervisorId(faculty_supervisor_id, pageable);
		return internships;
	}

	@GetMapping("/count/approved")
	public ResponseEntity<Long> countApprovedInternships() {
		return ResponseEntity.ok(internshipService.countApprovedInternships());
	}
}
