package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import tr.edu.ogu.ceng.dto.StudentDto;
import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@GetMapping("/{id}")
	public ResponseEntity<StudentDto> getStudent(@PathVariable(name = "id") long id) {
		StudentDto studentDto = studentService.getStudent(id);
		return ResponseEntity.ok(studentDto);
	}

	@GetMapping("/getAll")
	public Page<StudentDto> getStudents(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<StudentDto> students = studentService.getAllStudents(pageable);
		return students;
	}

	@GetMapping("/getByName/{studentName}")
	public Page<StudentDto> getStudentsByName(@PathVariable(name = "studentName") String studentName, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<StudentDto> students = studentService.getStudentsByName(pageable, studentName);
		return students;
	}

	@PostMapping()
	public StudentDto addStudent(@RequestBody StudentDto studentDto) {
		return studentService.addStudent(studentDto);
	}

	@PutMapping()
	public StudentDto updateStudent(@RequestBody StudentDto studentDto) {
		return studentService.updateStudent(studentDto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteStudent(@PathVariable(name = "id") Long id) {
		return studentService.deleteStudent(id);
	}

	@PostMapping("/registerasstudent")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<StudentDto> registerAsStudent(@RequestBody StudentDto request) {
		StudentDto response = studentService.registerAsStudent(request);
		return ResponseEntity.ok(response);
	}

}
