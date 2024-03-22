package tr.edu.ogu.ceng.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

import tr.edu.ogu.ceng.dto.UserDto;
import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.requests.StudentRequestDto;
import tr.edu.ogu.ceng.dto.requests.UserRequestDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.EmailService;
import tr.edu.ogu.ceng.service.FacultySupervisorService;
import tr.edu.ogu.ceng.service.PasswordGeneratorService;
import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.util.PageableUtil;

import java.security.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/facultySupervisor")

public class FacultySupervisorController {

	@Autowired
	private FacultySupervisorService facultySupervisorService;

	@Autowired
	private PasswordGeneratorService passwordGeneratorService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/supervisors")
	public Page<FacultySupervisorResponseDto> getAllFacultySupervisors(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<FacultySupervisorResponseDto> facultySupervisors = facultySupervisorService
				.getAllFacultySupervisors(pageable);
		return facultySupervisors;
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

	@PostMapping ("/addStudent")
	public ResponseEntity<?> addStudentUnderFacultySupervisor(@RequestBody StudentRequestDto studentRequestDto) {



		String password = passwordGeneratorService.generateSecurePassword();
		studentRequestDto.setPassword(password);
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(studentRequestDto.getUser(), User.class);
		Student student = modelMapper.map(studentRequestDto, Student.class);
		student.setUser(user);
		StudentResponseDto studentResponseDto = modelMapper.map(studentService.addStudent(student), StudentResponseDto.class);

		if (studentResponseDto != null) {
			emailService.sendPasswordViaEmail(user,student.getName(),student.getSurname());
			System.out.println("Başarıyla kaydoldu");
			return ResponseEntity.ok(studentResponseDto); // 200 OK
		} else {
			return ResponseEntity.notFound().build(); // 404 Not Found
		}
	}

 /*
	@GetMapping ("/sendmail")
	public boolean addStudentUnderFacultySupervisor() {

        User user = new User();
		user.setPassword(passwordGeneratorService.generateSecurePassword());
        user.setId(1L);
        user.setUsername("example_user");
        user.setEmail("danisss.zeynep@gmail.com");
        user.setUserType(UserType.STUDENT);
        user.setCreateDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());





		emailService.sendPasswordViaEmail(user,"zeynep","Danış");

       return true;



	}
*/


	@PutMapping
	public ResponseEntity<FacultySupervisorResponseDto> updateFacultySupervisor(
			@RequestBody FacultySupervisorRequestDto facultySupervisorRequestDto) {
		FacultySupervisorResponseDto updatedFacultySupervisor = facultySupervisorService
				.updateFacultySupervisor(facultySupervisorRequestDto);
		return ResponseEntity.ok(updatedFacultySupervisor);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FacultySupervisorResponseDto> getFacultySupervisor(@PathVariable(name = "id") long id) {
		FacultySupervisorResponseDto facultySupervisorResponseDto = facultySupervisorService.getFacultySupervisor(id);
		return ResponseEntity.ok(facultySupervisorResponseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFacultySupervisor(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(facultySupervisorService.deleteFacultySupervisor(id));
	}

	@GetMapping("/byUserId/{userId}")
	public FacultySupervisorResponseDto getFacultySupervisorByUserId(@PathVariable Long userId) {
		return facultySupervisorService.getFacultySupervisorByUserId(userId);
	}
}