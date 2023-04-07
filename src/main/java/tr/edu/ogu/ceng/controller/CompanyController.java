package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.edu.ogu.ceng.dto.CompanyDto;
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

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable(name = "id") long id) throws Exception {
		Company company = companyService.getCompany(id);
		CompanyDto companydto = new CompanyDto(company);
		return ResponseEntity.ok(companydto);
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<Page<Company>> searchCompanies(@PathVariable String name,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "name") String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Company> companies = companyService.searchCompanies(name, pageable);

		return ResponseEntity.ok(companies);
	}

}