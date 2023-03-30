package tr.edu.ogu.ceng.controller;


import java.util.List;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.CompanyService;

@RestController
@RequestMapping(value="/api/company",produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {
	@Autowired
    CompanyService companyService;
     
    @PutMapping("/{id}")
	public ResponseEntity<Company> updateCompany(@RequestBody Company company, @PathVariable(name="id") int id) {
		Company newCompany = companyService.updateCompany(company, id);
		
    	return ResponseEntity.ok(newCompany);
	}
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Company>> searchCompanies(@PathVariable String name) {
        List<Company> companies = companyService.searchCompanies(name);
        return ResponseEntity.ok(companies);
    }
}