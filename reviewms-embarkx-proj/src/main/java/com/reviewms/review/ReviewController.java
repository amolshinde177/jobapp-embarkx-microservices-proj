package com.reviewms.review;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reviewms.messaging.ReviewMessageProducer;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ReviewMessageProducer reviewMessageProducer;

	@GetMapping()
	public ResponseEntity<List<Review>> getReviews(@RequestParam Long companyId) {
		try {
			List<Review> reviews = reviewService.getReviews(companyId);
			return new ResponseEntity<>(reviews, HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getStatusCode());
		}
	}

	@PostMapping()
	public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
		try {
			reviewService.addReview(companyId, review);
			reviewMessageProducer.sendMessage(review);
			return new ResponseEntity<String>("Review added successfully", HttpStatus.CREATED);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
		try {
			Review review = reviewService.getReviewById(reviewId);
			return new ResponseEntity<Review>(review, HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(e.getStatusCode());
		}
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
		try {
			reviewService.updateReview(reviewId, review);
			return new ResponseEntity<String>("Review updated successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
		try {
			reviewService.deleteReview(reviewId);
			return new ResponseEntity<String>("Review Deleted Successfully", HttpStatus.OK);
		} catch (ResponseStatusException e) {
			return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
		}
	}

	@GetMapping("/averageRating")
	public Double getAverageRating(@RequestParam Long companyId) {
		try {
			List<Review> reviews = reviewService.getReviews(companyId);
			Double averageRating = reviews.stream().mapToDouble(Review::getRating).average().orElse(0);
			return averageRating;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
