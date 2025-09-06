package com.jobapp.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public List<Company> getCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public void createCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	public Company getCompanyById(Long id) {
		Company company = companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with given id: " + id));
		return company;
	}

	@Override
	public void deleteCompanyById(Long id) {
		companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with given id"));
		companyRepository.deleteById(id);
	}

	@Override
	public void updateCompany(Long id, Company company) {
		Company existingCompany = companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with given id: " + id));

		if (!ObjectUtils.isEmpty(company.getName())) {
			existingCompany.setName(company.getName());
		}
		if (!ObjectUtils.isEmpty(company.getDescription())) {
			existingCompany.setDescription(company.getDescription());
		}
		if (!ObjectUtils.isEmpty(company.getJobs())) {
			existingCompany.setJobs(company.getJobs());
		}
		companyRepository.save(existingCompany);
	}

}
