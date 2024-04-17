package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.dto.FacultyDto;
import tr.edu.ogu.ceng.dto.requests.FacultyRequestDto;
import tr.edu.ogu.ceng.security.UserPrincipal;
import tr.edu.ogu.ceng.service.AuthenticationService;
import tr.edu.ogu.ceng.service.FacultyService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController

@RequestMapping("/api/faculty")

public class FacultyController {
	@Autowired
	private FacultyService facultyService;
	
	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping("/getAll")
	public Page<FacultyDto> getFaculties(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<FacultyDto> facultyDtos = facultyService.getFaculties(pageable);
		return facultyDtos;
	}

	@PostMapping
	public FacultyDto addFaculty(@RequestBody FacultyDto facultyDto) {
		return facultyService.addFaculty(facultyDto);
	}

	@PutMapping("/admin")
	public FacultyDto updateFaculty(@RequestBody FacultyRequestDto facultyDto) {
		return facultyService.updateFaculty(facultyDto);
	}

	@DeleteMapping("/admin/{id}")
	public boolean deleteFaculty(@PathVariable(name = "id") long id) {
		return facultyService.deleteFaculty(id);
	}
	
	@GetMapping("/")
	public ResponseEntity<FacultyDto> getById() {
	    Long userId = authenticationService.getCurrentUserId();
	    if (userId == null) {
	        // Kullanıcı oturum açmamış veya kimlik doğrulanmamışsa uygun bir hata işleyin veya null döndürün
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    return ResponseEntity.ok(facultyService.getFacultyById(userId));
	}


	@GetMapping("/count")
	public ResponseEntity<Long> getFacultyCount() {
		Long count = facultyService.countFaculties();
		return ResponseEntity.ok(count);
	}
}
