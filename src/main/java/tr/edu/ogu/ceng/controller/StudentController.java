package tr.edu.ogu.ceng.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.requests.UpdateStudentRequest;
import tr.edu.ogu.ceng.dto.responses.UpdateStudentResponse;
import tr.edu.ogu.ceng.service.StudentService;

@RestController
@RequestMapping("api/students") 
@AllArgsConstructor
public class StudentController {

	private StudentService studentService;
	
	 @PutMapping("/update")
	public UpdateStudentResponse update (long id, @RequestBody  UpdateStudentRequest updateStudentRequest)
	{
		 return studentService.update(id,updateStudentRequest);
	}
}
