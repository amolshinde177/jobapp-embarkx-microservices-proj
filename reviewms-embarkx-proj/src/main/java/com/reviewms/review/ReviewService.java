package com.reviewms.review;

import java.util.List;

public interface ReviewService {

	List<Review> getReviews(Long companyId);

	void addReview(Long companyId, Review review);

	Review getReviewById(Long reviewId);

	void updateReview(Long reviewId, Review review);

	void deleteReview(Long reviewId);

}
