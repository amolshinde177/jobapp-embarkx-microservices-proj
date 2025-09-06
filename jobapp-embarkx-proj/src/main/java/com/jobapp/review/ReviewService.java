package com.jobapp.review;

import java.util.List;

public interface ReviewService {

	List<Review> getReviews(Long companyId);

	void addReview(Long companyId, Review review);

	Review getReviewById(Long companyId, Long reviewId);

	void updateReview(Long companyId, Long reviewId, Review review);

	void deleteReview(Long companyId, Long reviewId);

}
