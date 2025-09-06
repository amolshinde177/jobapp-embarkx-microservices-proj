package com.jobms.job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.jobms.job.clients.CompanyClient;
import com.jobms.job.clients.ReviewClient;
import com.jobms.job.dto.JobDTO;
import com.jobms.job.external.Company;
import com.jobms.job.external.Review;
import com.jobms.mapper.JobDTOMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CompanyClient companyClient;

	@Autowired
	private ReviewClient reviewClient;

	Long attempt = 0l;

	@Override
//	@CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//	@Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	@RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	public List<JobDTO> fetchJobs() {
		System.out.println("Attempt: " + ++attempt);
		List<Job> jobs = jobRepository.findAll();

		List<JobDTO> jobDTOs = jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
		return jobDTOs;
	}

	public List<String> companyBreakerFallback(Exception e) {
		List<String> list = new ArrayList<>();
		list.add("dummy");
		return list;
	}

	public JobDTO convertToDTO(Job job) {
		Company company = companyClient.getCompany(job.getCompanyId());
//		Company company = restTemplate.getForObject("http://COMPANYMS-EMBARKX-PROJ/companies/" + job.getCompanyId(), Company.class);

//		ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEWMS-EMBARKX-PROJ/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
//				new ParameterizedTypeReference<List<Review>>() {
//				});

		List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

//		List<Review> reviews = reviewResponse.getBody();

		JobDTO jobDTO = JobDTOMapper.mapJobDTOMapper(job, company, reviews);

		return jobDTO;
	}

	@Override
	public void createJob(Job job) {
		jobRepository.save(job);
	}

	@Override
	public JobDTO getJobById(Long id) {
		Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with id " + id + " not found"));

		Company company = restTemplate.getForObject("http://COMPANYMS-EMBARKX-PROJ/companies/" + job.getCompanyId(), Company.class);

		ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEWMS-EMBARKX-PROJ/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Review>>() {
				});

		List<Review> reviews = reviewResponse.getBody();

		JobDTO jobDTO = JobDTOMapper.mapJobDTOMapper(job, company, reviews);
		return jobDTO;
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
