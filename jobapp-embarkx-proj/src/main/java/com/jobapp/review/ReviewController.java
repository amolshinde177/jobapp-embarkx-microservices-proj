package com.jobapp.review;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> GetReviews(@PathVariable Long companyId) {
		try {
			List<Review> reviews = reviewService.getReviews(companyId);
			return new ResponseEntity<>(reviews, HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getStatusCode());
		}
	}

	@PostMapping("/reviews")
	public ResponseEntity<String> addReview(@PathVariable Long companyId, @RequestBody Review review) {
		try {
			reviewService.addReview(companyId, review);
			return new ResponseEntity<String>("Review added successfully", HttpStatus.CREATED);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long companyId, @PathVariable Long reviewId) {
		try {
			Review review = reviewService.getReviewById(companyId, reviewId);
			return new ResponseEntity<Review>(review, HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getStatusCode());
		}
	}

	@PutMapping("/reviews/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review review) {
		try {
			reviewService.updateReview(companyId, reviewId, review);
			return new ResponseEntity<String>("Review updated successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
		try {
			reviewService.deleteReview(companyId, reviewId);
			return new ResponseEntity<String>("Review Deleted Successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

}
