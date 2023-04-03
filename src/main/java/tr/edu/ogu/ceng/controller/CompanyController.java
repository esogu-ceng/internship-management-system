package tr.edu.ogu.ceng.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.model.Company;
import tr.edu.ogu.ceng.service.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
	@Autowired
    CompanyService companyService;
     
    @PutMapping
	public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
		Company newCompany = companyService.updateCompany(company);
    	return ResponseEntity.ok(newCompany);
	}
}