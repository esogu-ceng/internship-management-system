package tr.edu.ogu.ceng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.Student;
import tr.edu.ogu.ceng.service.FacultyService;

@RestController

@RequestMapping("/api/faculty")

public class FacultyController {
    @Autowired
    private FacultyService facultyService;

	@GetMapping("/getAll")
	public ResponseEntity<List<Faculty>> getFaculties() {
		return ResponseEntity.ok(facultyService.getFaculties());
	}

    @PostMapping()
    public ResponseEntity<Faculty> addFaculty (@RequestBody Faculty faculty){
        facultyService.addFaculty(faculty);
        return new ResponseEntity<>(faculty, HttpStatus.CREATED);
    }
    
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFaculty(@PathVariable(name="id") long id) {
		return ResponseEntity.ok(facultyService.deleteFaculty(id));
	}
}
 