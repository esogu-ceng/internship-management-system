package tr.edu.ogu.ceng.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.requests.StudentRequestDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.InternshipStatus;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.security.AuthService;
import tr.edu.ogu.ceng.security.UserPrincipal;
import tr.edu.ogu.ceng.service.FacultySupervisorService;
import tr.edu.ogu.ceng.service.InternshipService;
import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/facultySupervisor")

public class FacultySupervisorController {

	private FacultySupervisorService facultySupervisorService;
	private StudentService studentService;
	private InternshipService internshipService;
	private AuthService authenticationService;

	@GetMapping("/supervisors")
	public Page<FacultySupervisorResponseDto> getAllFacultySupervisors(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		ModelMapper modelMapper = new ModelMapper();
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<FacultySupervisor> facultySupervisor = facultySupervisorService.getAllFacultySupervisors(pageable);
		Page<FacultySupervisorResponseDto> facultySupervisorsdto = facultySupervisor
				.map(facultySupervisormap -> modelMapper.map(facultySupervisormap, FacultySupervisorResponseDto.class));
		return facultySupervisorsdto;

	}

	@PostMapping("/saveFacultysupervisor")
	public FacultySupervisorResponseDto addFacultySupervisor(
			@RequestBody @Validated FacultySupervisorRequestDto facultySupervisorRequestDto) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(facultySupervisorRequestDto.getUser(), User.class);

		FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisorRequestDto, FacultySupervisor.class);
		facultySupervisor.setUser(user);
		return modelMapper.map(facultySupervisorService.addFacultySupervisor(facultySupervisor),
				FacultySupervisorResponseDto.class);
	}

	@PostMapping("/addStudent")
	public StudentResponseDto addStudentUnderFacultySupervisor(@RequestBody StudentRequestDto studentRequestDto) {

		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(studentRequestDto.getUser(), User.class);
		Student student = modelMapper.map(studentRequestDto, Student.class);
		student.setUser(user);
		return modelMapper.map(studentService.addStudentAndSendMail(student), StudentResponseDto.class);

	}

	@PutMapping
	public ResponseEntity<FacultySupervisorResponseDto> updateFacultySupervisor(
			@RequestBody FacultySupervisorRequestDto facultySupervisorRequestDto) {
		ModelMapper modelMapper = new ModelMapper();
		FacultySupervisor updatedFacultySupervisor = facultySupervisorService
				.updateFacultySupervisor(modelMapper.map(facultySupervisorRequestDto, FacultySupervisor.class));
		return ResponseEntity.ok(modelMapper.map(updatedFacultySupervisor, FacultySupervisorResponseDto.class));
	}

	@GetMapping("/{id}")
	public ResponseEntity<FacultySupervisorResponseDto> getFacultySupervisor(@PathVariable(name = "id") long id,
			Authentication authentication) {
		UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
		ModelMapper modelMapper = new ModelMapper();
		// İsteği yapan kullanıcı yetkilendirilmiş mi kontrolü
		if (!currentUser.getUser().getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		FacultySupervisorResponseDto facultySupervisorResponseDto = modelMapper
				.map(facultySupervisorService.getFacultySupervisor(id), FacultySupervisorResponseDto.class);
		return ResponseEntity.ok(facultySupervisorResponseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFacultySupervisor(@PathVariable(name = "id") long id,
			Authentication authentication) {
		UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
		// İsteği yapan kullanıcı yetkilendirilmiş mi kontrolü
		if (!currentUser.getUser().getId().equals(id)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(facultySupervisorService.deleteFacultySupervisor(id));
	}

	@PutMapping("/faculty-approved/{id}")
	public InternshipStatus approveInternshipByFaculty(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.FACULTY_APPROVED);
	}

	@PutMapping("/ongoing/{id}")
	public InternshipStatus markInternshipAsOngoing(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.ONGOING);
	}

	@PutMapping("/faculty-evaluation-stage/{id}")
	public InternshipStatus moveToInternshipEvaluationStageByFaculty(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.FACULTY_EVALUATION_STAGE);
	}

	@PutMapping("/faculty-rejected/{id}")
	public InternshipStatus rejectInternshipByFaculty(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.FACULTY_REJECTED);
	}

	@PutMapping("/faculty-invalid/{id}")
	public InternshipStatus markInternshipAsInvalidByFaculty(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.FACULTY_INVALID);
	}

	@PutMapping("/success/{id}")
	public InternshipStatus markInternshipAsSuccess(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.SUCCESS);
	}

	@PutMapping("/canceled/{id}")
	public InternshipStatus cancelInternship(@PathVariable(name = "id") long id) {
		return internshipService.chanceInternshipStatus(id, InternshipStatus.CANCELED);
	}
}