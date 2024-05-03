package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.service.CompanyService;
import tr.edu.ogu.ceng.service.FacultyService;
import tr.edu.ogu.ceng.service.InternshipService;
import tr.edu.ogu.ceng.service.StudentService;

@AllArgsConstructor
@RestController
@RequestMapping("/public/api")
public class PublicController {

	private InternshipService internshipService;
	private CompanyService companyService;
	private StudentService studentService;
	private FacultyService facultyService;

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

	@GetMapping("/count/faculty")
	public ResponseEntity<Long> getFacultyCount() {
		Long count = facultyService.countFaculties();
		return ResponseEntity.ok(count);
	}

	@GetMapping("/count/company")
	public ResponseEntity<Long> getCompanyCount() {
		Long count = companyService.countCompanies();
		return ResponseEntity.ok(count);
	}

	@GetMapping("/count/student")
	public ResponseEntity<Long> getStudentCount() {
		Long count = studentService.countStudents();
		return ResponseEntity.ok(count);
	}

}