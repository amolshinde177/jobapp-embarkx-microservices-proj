package com.jobapp.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import com.jobapp.company.Company;
import com.jobapp.company.CompanyService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CompanyService companyService;

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
		Company company = companyService.getCompanyById(companyId);
		if (company == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with given companyId: " + companyId);
		}
		review.setCompany(company);
		reviewRepository.save(review);
	}

	@Override
	public Review getReviewById(Long companyId, Long reviewId) {
		List<Review> reviews = getReviews(companyId);
		Review review = reviews.stream().filter(r -> r.getId().equals(reviewId)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found for given reviewId: " + reviewId));
		return review;
	}

	@Override
	public void updateReview(Long companyId, Long reviewId, Review review) {
		Review existingReview = getReviewById(companyId, reviewId);

		if (!ObjectUtils.isEmpty(review.getRating())) {
			existingReview.setRating(review.getRating());
		}
		if (!ObjectUtils.isEmpty(review.getTitle())) {
			existingReview.setTitle(review.getTitle());
		}
		if (!ObjectUtils.isEmpty(review.getDescription())) {
			existingReview.setDescription(review.getDescription());
		}
		reviewRepository.save(existingReview);
	}

	@Override
	public void deleteReview(Long companyId, Long reviewId) {
		Review review = getReviewById(companyId, reviewId);
		Company company = companyService.getCompanyById(companyId);
		company.getReviews().remove(review);
		companyService.updateCompany(company.getId(), company);
		reviewRepository.deleteById(reviewId);
	}

}
