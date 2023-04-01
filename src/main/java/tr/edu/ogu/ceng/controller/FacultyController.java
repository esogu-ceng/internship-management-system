package tr.edu.ogu.ceng.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.requests.CreateFacultyRequest;
import tr.edu.ogu.ceng.dto.responses.CreateFacultyResponse;
import tr.edu.ogu.ceng.service.FacultyService;


@RestController
@RequestMapping("api/faculties") 

public class FacultyController {
	
	private FacultyService facultyService;

	public FacultyController(FacultyService facultyService) {
		super();
		this.facultyService = facultyService;
	}

	@PostMapping("/add")
	public CreateFacultyResponse add (@RequestBody CreateFacultyRequest createFacultyRequest)
	{
		 return facultyService.add(createFacultyRequest);
	}

}
