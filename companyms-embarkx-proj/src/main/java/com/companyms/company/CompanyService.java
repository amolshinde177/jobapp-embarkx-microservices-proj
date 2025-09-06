package com.companyms.company;

import java.util.List;

import com.companyms.dto.ReviewMessage;

public interface CompanyService {

	List<Company> getCompanies();

	void createCompany(Company company);

	Company getCompanyById(Long id);

	void deleteCompanyById(Long id);

	void updateCompany(Long id, Company company);

	void updateCompanyRating(ReviewMessage reviewMessage);

}
