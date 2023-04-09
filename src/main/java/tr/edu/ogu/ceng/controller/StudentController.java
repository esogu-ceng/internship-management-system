package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import tr.edu.ogu.ceng.dto.StudentDto;

import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable(name = "id") long id){
    	Student student = studentService.getStudent(id);
    	return ResponseEntity.ok(student);
    }

	@GetMapping("/getAll")
	public List<Student> getAll() {
		return studentService.getAllStudents();
	}

	@PostMapping("")
	public Student addStudent(@RequestBody Student student) {
		return studentService.addStudent(student);
	}

	@PutMapping("/{id}")
	public Student updateStudent(@RequestBody Student student, @PathVariable(name = "id") long id) {
		student.setId(id);
		return studentService.updateStudent(student);
	}

	@DeleteMapping("/{id}")
	public boolean deleteInternship(@PathVariable(name = "id") Long id) {
		return studentService.deleteStudent(id);
	}

	@PostMapping("/registerasstudent")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<StudentDto> registerAsStudent(@RequestBody StudentDto request) {
		StudentDto response = studentService.registerAsStudent(request);
		return ResponseEntity.ok(response);
	}
	
}
