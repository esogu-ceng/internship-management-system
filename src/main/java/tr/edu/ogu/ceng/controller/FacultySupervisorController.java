package tr.edu.ogu.ceng.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.requests.FacultySupervisorRequestDto;
import tr.edu.ogu.ceng.dto.responses.FacultySupervisorResponseDto;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.User;
import tr.edu.ogu.ceng.security.UserPrincipal;
import tr.edu.ogu.ceng.service.FacultySupervisorService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/api/facultySupervisor")

public class FacultySupervisorController {

	@Autowired
	private FacultySupervisorService facultySupervisorService;

	@GetMapping("/supervisors")
	public Page<FacultySupervisorResponseDto> getAllFacultySupervisors(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<FacultySupervisorResponseDto> facultySupervisors = facultySupervisorService
				.getAllFacultySupervisors(pageable);
		return facultySupervisors;
	}

	@PostMapping("/saveFacultysupervisor")
	public FacultySupervisorResponseDto addFacultySupervisor(
			@RequestBody @Validated FacultySupervisorRequestDto facultySupervisorRequestDto) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(facultySupervisorRequestDto.getUser(), User.class);

		FacultySupervisor facultySupervisor = modelMapper.map(facultySupervisorRequestDto, FacultySupervisor.class);
		facultySupervisor.setUser(user);
		return modelMapper.map(facultySupervisorService.addFacultySupervisor(facultySupervisor),
				FacultySupervisorResponseDto.class);
	}

	@PutMapping
	public ResponseEntity<FacultySupervisorResponseDto> updateFacultySupervisor(
			@RequestBody FacultySupervisorRequestDto facultySupervisorRequestDto) {
		FacultySupervisorResponseDto updatedFacultySupervisor = facultySupervisorService
				.updateFacultySupervisor(facultySupervisorRequestDto);
		return ResponseEntity.ok(updatedFacultySupervisor);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FacultySupervisorResponseDto> getFacultySupervisor(@PathVariable(name = "id") long id, Authentication authentication) {
	    UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
	    // İsteği yapan kullanıcı yetkilendirilmiş mi kontrolü
	    if (!currentUser.getUser().getId().equals(id)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    FacultySupervisorResponseDto facultySupervisorResponseDto = facultySupervisorService.getFacultySupervisor(id);
	    return ResponseEntity.ok(facultySupervisorResponseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFacultySupervisor(@PathVariable(name = "id") long id, Authentication authentication) {
	    UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
	    // İsteği yapan kullanıcı yetkilendirilmiş mi kontrolü
	    if (!currentUser.getUser().getId().equals(id)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    return ResponseEntity.ok(facultySupervisorService.deleteFacultySupervisor(id));
	}

	@RequestMapping(value = "/byUserId/{userId}", method = RequestMethod.GET)
	public ResponseEntity<FacultySupervisorResponseDto> getFacultySupervisorByUserId(@PathVariable Long userId, Authentication authentication) {
	    UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();
	    if (currentUser.getUser().getId()!= userId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    return ResponseEntity.ok(facultySupervisorService.getFacultySupervisorByUserId(userId));
	}

}