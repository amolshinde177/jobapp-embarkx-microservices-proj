package com.jobms.job.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobms.job.external.Review;

@FeignClient(name = "REVIEWMS-EMBARKX-PROJ", url = "${review-service.url}")
public interface ReviewClient {

	@GetMapping("/reviews")
	public List<Review> getReviews(@RequestParam Long companyId);

}
