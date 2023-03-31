package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.service.FacultyService;

@RestController
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @PostMapping("/saveFaculty")
    public ResponseEntity<Faculty> addFaculty (@RequestBody Faculty faculty){
        Faculty faculty1 = facultyService.saveFaculty(faculty);
        return new ResponseEntity<>(faculty1, HttpStatus.CREATED);
    }
}
