package com.jobapp.job;

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
public class JobController {

	@Autowired
	private JobService jobService;

	@GetMapping("/jobs")
	public ResponseEntity<List<Job>> getJobs() {
		return ResponseEntity.ok(jobService.fetchJobs());
	}

	@PostMapping("/jobs")
	public ResponseEntity<String> createJobs(@RequestBody Job job) {
		jobService.createJob(job);
		return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
	}

	@GetMapping("/jobs/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable Long id) {
		Job job = jobService.getJobById(id);
		if (job != null) {
			return new ResponseEntity<>(job, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

	@PutMapping("jobs/{id}")
	public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job) {
		try {
			jobService.updateJob(id, job);
			return new ResponseEntity<>("Job with id " + id + " updated successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

}
