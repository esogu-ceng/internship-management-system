package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	@Autowired
    CompanyService companyService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable(name="id") long id) {
        CompanyDto company = companyService.getCompany(id);
        return ResponseEntity.ok(company);
    }
}