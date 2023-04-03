package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.Student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {


	@Autowired
	StudentService studentService;

	@GetMapping("/{id}")
	public Student getStudent(@PathVariable(name = "id") int id) {
		return studentService.getStudent(id);
	}

	@PostMapping()
	public Student addStudent(@RequestBody Student student) {
		return studentService.addStudent(student);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteStudent(@PathVariable(name="id") long id) {
		return ResponseEntity.ok(studentService.deleteStudent(id));
	}
}
