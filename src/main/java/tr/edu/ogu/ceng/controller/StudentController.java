package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.service.StudentService;

@RestController
@RequestMapping("/api/Student")
public class StudentController {

	@Autowired
	StudentService service;

	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Student> getStudent() {
		return service.getStudent();
	}

	@GetMapping("/{id}")
	public Student getStudent(@PathVariable(name = "id") int id) {
		return service.getStudent(id);
	}

	@PostMapping("")
	public int addStudent(@RequestBody Student student) {
		return service.addStudent(student);
	}
}
