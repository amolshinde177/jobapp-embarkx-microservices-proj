package com.jobms.mapper;

import java.util.List;

import com.jobms.job.Job;
import com.jobms.job.dto.JobDTO;
import com.jobms.job.external.Company;
import com.jobms.job.external.Review;

public class JobDTOMapper {

	public static JobDTO mapJobDTOMapper(Job job, Company company, List<Review> reviews) {
		JobDTO jobDTO = new JobDTO();

		jobDTO.setId(job.getId());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setTitle(job.getDescription());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setCompany(company);
		jobDTO.setReviews(reviews);

		return jobDTO;
	}

}
