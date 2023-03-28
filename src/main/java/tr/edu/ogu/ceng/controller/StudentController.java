package tr.edu.ogu.ceng.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.service.StudentService;
import tr.edu.ogu.ceng.model.Student;


@RestController
@RequestMapping("/api/students")
public class StudentController {
	@Autowired
    StudentService studentService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable(name = "id") long id){
    	Student student = studentService.getStudent(id);
    	return ResponseEntity.ok(student);
    }
}
