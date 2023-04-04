package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.CompanyDto;
import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
	
	@Autowired
    CompanyService companyService;
    
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable(name="id") long id) throws Exception {
    	Company company = companyService.getCompany(id);
        CompanyDto companydto = new CompanyDto(company);
        return ResponseEntity.ok(companydto);
    }
}