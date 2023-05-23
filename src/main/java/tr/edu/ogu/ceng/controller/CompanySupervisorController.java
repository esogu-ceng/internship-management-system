package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.dto.CompanySupervisorDto;
import tr.edu.ogu.ceng.service.CompanySupervisorService;
import tr.edu.ogu.ceng.util.PageableUtil;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/company-supervisor")
public class CompanySupervisorController {
	@Autowired
    private final CompanySupervisorService service;
    
    @GetMapping
    public Page<CompanySupervisorDto> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "name") String sortBy){
    	Pageable pageable = PageableUtil.createPageRequest(pageNo, limit, sortBy);
		return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public CompanySupervisorDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompanySupervisorDto add(@RequestBody CompanySupervisorDto request){
        return service.add(request);
    }

    @PutMapping
    public CompanySupervisorDto update(@RequestBody CompanySupervisorDto request){
        return service.update(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/getCompanySupervisorsByCompanyId/{companyId}")
    public List<CompanySupervisorDto> getCompanySupervisorsByCompanyId(@PathVariable(name = "companyId") Long companyId){
        return service.getCompanySupervisorsByCompanyId(companyId);
    }
}