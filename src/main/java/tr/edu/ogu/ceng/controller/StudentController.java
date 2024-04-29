package tr.edu.ogu.ceng.controller;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.dto.requests.StudentRequestDto;
import tr.edu.ogu.ceng.dto.responses.StudentResponseDto;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.service.AuthenticationService;
import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@Autowired
	AuthenticationService authenticationService;

	@GetMapping
	public ResponseEntity<StudentResponseDto> getStudent() {
		Long userId = authenticationService.getCurrentUserId();

		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Student student = studentService.getStudent(userId);
		ModelMapper modelMapper = new ModelMapper();
		StudentResponseDto studentDto = modelMapper.map(student, StudentResponseDto.class);
		return ResponseEntity.ok(studentDto);
	}

	@GetMapping("/getAll")
	public Page<StudentResponseDto> getStudents(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		ModelMapper modelMapper = new ModelMapper();
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<Student> students = studentService.getAllStudents(pageable);
		Page<StudentResponseDto> studentDtos = students
				.map(student -> modelMapper.map(student, StudentResponseDto.class));
		return studentDtos;
	}

	/**
	 * Search student by name, surname or student number.
	 * 
	 * @param keyWord
	 * @param pageNo
	 * @param limit
	 * @param sortBy
	 * @return Page<StudentResponseDto>
	 */
	@GetMapping("/search/{keyWord}")
	public Page<StudentResponseDto> searchStudent(@PathVariable(name = "keyWord") String keyWord,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "name") String sortBy) {
		ModelMapper modelMapper = new ModelMapper();
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<Student> students = studentService.searchStudent(pageable, keyWord);
		Page<StudentResponseDto> studentResponseDtos = students
				.map(student -> modelMapper.map(student, StudentResponseDto.class));
		return studentResponseDtos;
	}

	@PostMapping
	public StudentResponseDto addStudent(@RequestBody StudentRequestDto studentRequestDto) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(studentRequestDto.getUser(), User.class);
		Student student = modelMapper.map(studentRequestDto, Student.class);
		student.setUser(user);
		return modelMapper.map(studentService.addStudent(student), StudentResponseDto.class);
	}

	@PutMapping
	public StudentResponseDto updateStudent(@RequestBody StudentRequestDto studentDto) {
		ModelMapper modelMapper = new ModelMapper();
		Student updateStudent = studentService.updateStudent(modelMapper.map(studentDto, Student.class));
		return modelMapper.map(updateStudent, StudentResponseDto.class);
	}

	@GetMapping("/supervisor")
	public Page<StudentResponseDto> getAllStudentsByFacultySupervisorId(
	        @RequestParam(defaultValue = "0") Integer pageNo,
	        @RequestParam(defaultValue = "10") Integer limit,
	        @RequestParam(defaultValue = "id") String sortBy) {
	    Long facultySupervisorId = authenticationService.getCurrentUserId();
	    if (facultySupervisorId == null) {
	        return null; // veya uygun bir hata işleme mekanizması
	    }
	    Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
	    
	    // Öğrenci verilerini getir
	    Page<Student> studentsPage = studentService.getAllStudentsByFacultySupervisorId(facultySupervisorId, pageable);
	    
	    // StudentResponseDto'ya dönüştürme işlemi
	    Page<StudentResponseDto> studentResponsePage = studentsPage.map(student -> {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(student, StudentResponseDto.class);
	    });
	    
	    return studentResponsePage;
	}


	@DeleteMapping("/{id}")
	public boolean deleteStudent(@PathVariable(name = "id") Long id) {
		return studentService.deleteStudent(id);
	}

	@PostMapping("/registerasstudent")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<StudentResponseDto> registerAsStudent(@RequestBody StudentDto request) {

		StudentResponseDto response = studentService.registerAsStudent(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getCompanyCount() {
		Long count = studentService.countStudents();
		return ResponseEntity.ok(count);
	}

	@PostMapping("/{studentNo}/cv")
	public ResponseEntity<?> uploadCvToFIleSystem(@PathVariable String studentNo, @RequestParam("cv") MultipartFile file) throws IOException {
		String uploadCv = studentService.uploadCvToFileSystem(studentNo, file);

		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadCv);
	}

	@GetMapping("/{studentNo}/cv")
	public ResponseEntity<?> downloadCvFromFileSystem(@PathVariable String studentNo) throws IOException {
		byte[] cvData = studentService.downloadCvFromFileSystem(studentNo);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("application/pdf"))
				.body(cvData);
	}
}
