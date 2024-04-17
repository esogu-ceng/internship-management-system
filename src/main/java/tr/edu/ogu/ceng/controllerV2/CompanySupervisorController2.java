package tr.edu.ogu.ceng.controllerV2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.responses.InternshipResponseDto;
import tr.edu.ogu.ceng.service.InternshipService;
import tr.edu.ogu.ceng.util.PageableUtil;

@RestController
@RequestMapping("/company-supervisor/")
@AllArgsConstructor
public class CompanySupervisorController2 {

	InternshipService internshipService;

	@GetMapping("/internships")
	public Page<InternshipResponseDto> getInternships(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "id") String sortBy) {
		Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		Page<InternshipResponseDto> internships = internshipService
				.getAllInternshipsCompany(pageable);
		return internships;
	}

}