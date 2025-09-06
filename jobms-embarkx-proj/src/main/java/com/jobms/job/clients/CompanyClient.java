package com.jobms.job.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jobms.job.external.Company;

@FeignClient(name = "COMPANYMS-EMBARKX-PROJ", url = "${company-service.url}")
public interface CompanyClient {

	@GetMapping("/companies/{companyId}")
	public Company getCompany(@PathVariable Long companyId);

}
