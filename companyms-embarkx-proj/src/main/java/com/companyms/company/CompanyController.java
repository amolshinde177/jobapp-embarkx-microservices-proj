package com.companyms.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@GetMapping("/companies")
	public ResponseEntity<List<Company>> getCompanies() {
		return ResponseEntity.ok(companyService.getCompanies());
	}

	@PostMapping("/companies")
	public ResponseEntity<String> createCompany(@RequestBody Company company) {
		try {
			companyService.createCompany(company);
			return new ResponseEntity<String>("Company Created Successfully", HttpStatus.CREATED);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

	@GetMapping("/companies/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
		try {
			Company company = companyService.getCompanyById(id);
			return new ResponseEntity<>(company, HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getStatusCode());
		}
	}

	@DeleteMapping("/companies/{id}")
	public ResponseEntity<String> deleteCompanyById(@PathVariable Long id) {
		try {
			companyService.deleteCompanyById(id);
			return new ResponseEntity<String>("Company deleted successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getReason(), e.getStatusCode());
		}
	}

	@PutMapping("/companies/{id}")
	public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company) {
		try {
			companyService.updateCompany(id, company);
			return new ResponseEntity<String>("Company updated successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

}
