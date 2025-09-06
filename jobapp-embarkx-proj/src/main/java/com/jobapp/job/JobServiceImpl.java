package com.jobapp.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepository;

	@Override
	public List<Job> fetchJobs() {
		return jobRepository.findAll();
	}

	@Override
	public void createJob(Job job) {
		jobRepository.save(job);
	}

	@Override
	public Job getJobById(Long id) {
		Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with id " + id + " not found"));
		return job;
	}

	@Override
	public void deleteJobById(Long id) {
		Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with id " + id + " not found"));
		jobRepository.delete(job);
	}

	@Override
	public void updateJob(Long id, Job job) {
		Job existingJob = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with id " + id + " not found"));
		if (!ObjectUtils.isEmpty(job.getTitle())) {
			existingJob.setTitle(job.getTitle());
		}
		if (!ObjectUtils.isEmpty(job.getDescription())) {
			existingJob.setDescription(job.getDescription());
		}
		if (!ObjectUtils.isEmpty(job.getLocation())) {
			existingJob.setLocation(job.getLocation());
		}
		if (!ObjectUtils.isEmpty(job.getMinSalary())) {
			existingJob.setMinSalary(job.getMinSalary());
		}
		if (!ObjectUtils.isEmpty(job.getMaxSalary())) {
			existingJob.setMaxSalary(job.getMaxSalary());
		}
		jobRepository.save(existingJob);
	}

}
