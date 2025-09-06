package com.reviewms.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public List<Review> getReviews(Long companyId) {
		List<Review> reviews = reviewRepository.findByCompanyId(companyId);
		if (reviews != null && !reviews.isEmpty()) {
			return reviews;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found with given companyId: " + companyId);
		}
	}

	@Override
	public void addReview(Long companyId, Review review) {
		if (companyId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with given companyId: " + companyId);
		}
		review.setCompanyId(companyId);
		reviewRepository.save(review);
	}

	@Override
	public Review getReviewById(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found for given reviewId: " + reviewId));
		return review;
	}

	@Override
	public void updateReview(Long reviewId, Review updateReview) {
		Review existingReview = getReviewById(reviewId);

		if (!ObjectUtils.isEmpty(updateReview.getRating())) {
			existingReview.setRating(updateReview.getRating());
		}
		if (!ObjectUtils.isEmpty(updateReview.getTitle())) {
			existingReview.setTitle(updateReview.getTitle());
		}
		if (!ObjectUtils.isEmpty(updateReview.getDescription())) {
			existingReview.setDescription(updateReview.getDescription());
		}
		reviewRepository.save(existingReview);
	}

	@Override
	public void deleteReview(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}

}
