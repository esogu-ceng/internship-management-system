package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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

import tr.edu.ogu.ceng.dao.CompanyRepository;
import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.dao.StudentRepository;
import tr.edu.ogu.ceng.dao.UserRepository;
import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.dto.requests.InternshipRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseCompanyDto;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.service.CompanyService;
import tr.edu.ogu.ceng.service.FacultyService;
import tr.edu.ogu.ceng.service.InternshipService;
import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/public/api")
public class PublicController {
	@Autowired
	InternshipService internshipService;
	@Autowired
	CompanyService companyService;
	@Autowired
	StudentService studentService;
	@Autowired
	FacultyService facultyService;
	
	private StudentRepository studentRepository;
	private CompanyRepository companyRepository;
	private FacultySupervisorRepository facultySupervisorRepository;
	private UserRepository userRepository;
	private FacultyRepository facultyRepository;
	private  ModelMapper modelMapper;
	
	
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