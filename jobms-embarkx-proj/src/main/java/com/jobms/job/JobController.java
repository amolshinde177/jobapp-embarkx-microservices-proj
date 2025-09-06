package com.jobms.job;

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

import com.jobms.job.dto.JobDTO;

@RestController
public class JobController {

	@Autowired
	private JobService jobService;

	@GetMapping("/jobs")
	public ResponseEntity<List<JobDTO>> getJobs() {
		try {
			return ResponseEntity.ok(jobService.fetchJobs());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping("/jobs")
	public ResponseEntity<String> createJobs(@RequestBody Job job) {
		jobService.createJob(job);
		return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
	}

	@GetMapping("/jobs/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
		try {
			JobDTO jobDTO = jobService.getJobById(id);
			return new ResponseEntity<>(jobDTO, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/jobs/{id}")
	public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
		try {
			jobService.deleteJobById(id);
			return new ResponseEntity<>("Job with id" + id + " deleted successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

	@PutMapping("/jobs/{id}")
	public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job) {
		try {
			jobService.updateJob(id, job);
			return new ResponseEntity<>("Job with id " + id + " updated successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

}
