package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import tr.edu.ogu.ceng.security.UserPrincipal;
import tr.edu.ogu.ceng.service.AuthenticationService;
import tr.edu.ogu.ceng.service.InternshipService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/internship")
public class InternshipController {
	@Autowired
	InternshipService internshipService;
	/**
	 * Adds a new internship record.
	 * @param internshipDto
	 * @return ResponseEntity<InternshipResponseDto>
	 */
	@Autowired
	AuthenticationService authenticationService;
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

	@PutMapping("/applied/{id}")
	public InternshipStatus applyInternship(@PathVariable(name = "id") long id) {
	    return internshipService.chanceInternshipStatus(id, InternshipStatus.APPLIED);
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

	@GetMapping("/supervisor")
	public Page<InternshipResponseCompanyDto> getAllInternshipsByFacultySupervisorId(
	        @RequestParam(defaultValue = "0") Integer pageNo,
	        @RequestParam(defaultValue = "10") Integer limit,
	        @RequestParam(defaultValue = "id") String sortBy) {
	    Long facultySupervisorId = authenticationService.getCurrentUserId(); // Oturum açmış kullanıcının kimliğini alıyoruz
	    if (facultySupervisorId == null) {
	        // Kullanıcı oturum açmamışsa uygun bir hata işleyin veya null döndürün
	        // Burada size bağlı, örneğin ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() kullanabilirsiniz.
	        return null; // veya uygun bir hata işleme mekanizması
	    }
	    Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
	    return internshipService.getAllInternshipsByFacultySupervisorId(facultySupervisorId, pageable);
	}
	@GetMapping("/count/applied")
	public ResponseEntity<Long> countAppliedInternships() {
		return ResponseEntity.ok(internshipService.countAppliedInternships());
	}
	
	@GetMapping("/count/company-approved")
	public ResponseEntity<Long> countCompanyApprovedInternships() {
		return ResponseEntity.ok(internshipService.countCompanyApprovedInternships());
	}
	
	@GetMapping("/count/faculty-approved")
	public ResponseEntity<Long> countFacultyApprovedInternships() {
		return ResponseEntity.ok(internshipService.countFacultyApprovedInternships());
	}
	
	@GetMapping("/count/ongoing")
	public ResponseEntity<Long> countOngoingInternships() {
		return ResponseEntity.ok(internshipService.countOngoingInternships());
	}
	
	@GetMapping("/count/company-evaluation-stage")
	public ResponseEntity<Long> countCompanyEvaluationStageInternships() {
		return ResponseEntity.ok(internshipService.countCompanyEvaluationStageInternships());
	}
	
	@GetMapping("/count/faculty-evaluation-stage")
	public ResponseEntity<Long> countFacultyEvaluationStageInternships() {
		return ResponseEntity.ok(internshipService.countFacultyEvaluationStageInternships());
	}
	
	@GetMapping("/count/success")
	public ResponseEntity<Long> countSuccessInternships() {
		return ResponseEntity.ok(internshipService.countSuccessInternships());
	}
	
	@GetMapping("/count/faculty-rejected")
	public ResponseEntity<Long> countFacultyRejectedInternships() {
		return ResponseEntity.ok(internshipService.countFacultyRejectedInternships());
	}
	
	@GetMapping("/count/company-rejected")
	public ResponseEntity<Long> countCompanyRejectedInternships() {
		return ResponseEntity.ok(internshipService.countCompanyRejectedInternships());
	}
	
	@GetMapping("/count/faculty-invalid")
	public ResponseEntity<Long> countFacultyInvalidInternships() {
		return ResponseEntity.ok(internshipService.countFacultyInvalidInternships());
	}
	
	@GetMapping("/count/canceled")
	public ResponseEntity<Long> countCanceledInternships() {
		return ResponseEntity.ok(internshipService.countCanceledInternships());
	}
	
	@GetMapping("/count/DistinctStudents")
    public ResponseEntity<Long> countDistinctStudents() {
        long count = internshipService.countDistinctStudents();
        return ResponseEntity.ok(count);
    }
	
	@GetMapping("/count/all")
	public ResponseEntity<Long> countAllInternships() {
		return ResponseEntity.ok(internshipService.countAllInternships());
	}
	@GetMapping("/count-by-year")
	public List<Object[]> countInternshipsByYear() {
        return internshipService.countInternshipsByYear();
    }
	@GetMapping("/count-by-month")
	public List<Object[]> countInternshipsByMonth() {
        return internshipService.countInternshipsByMonth();
    }

	@GetMapping("/count/companyStudent")
	public List<Object[]> countApprovedInternshipforCompany() {
		return internshipService.countApprovedInternshipsforCompany();
	}
}
