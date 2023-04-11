package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.service.FacultyService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController

@RequestMapping("/api/faculty")

public class FacultyController {
	@Autowired
	private FacultyService facultyService;

	@GetMapping("/getAll")
	public Page<Faculty> getFaculties(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<Faculty> faculties = facultyService.getFaculties(pageable);
		return faculties;
	}

	@PostMapping()
	public Faculty addFaculty(@RequestBody Faculty faculty) {
		return facultyService.addFaculty(faculty);
	}

	@PutMapping("/{id}")
	public Faculty updateFaculty(@RequestBody Faculty faculty, @PathVariable(name = "id") long id) {
		faculty.setId(id);
		return facultyService.updateFaculty(faculty);
	}

	@DeleteMapping("/{id}")
	public boolean deleteFaculty(@PathVariable(name = "id") long id) {
		return facultyService.deleteFaculty(id);
	}

}
