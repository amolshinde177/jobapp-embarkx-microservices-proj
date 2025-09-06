package com.jobms.job;

import java.util.List;

import com.jobms.job.dto.JobDTO;

public interface JobService {

	List<JobDTO> fetchJobs();

	void createJob(Job job);

	JobDTO getJobById(Long id);

	void deleteJobById(Long id);

	void updateJob(Long id, Job job);

}
