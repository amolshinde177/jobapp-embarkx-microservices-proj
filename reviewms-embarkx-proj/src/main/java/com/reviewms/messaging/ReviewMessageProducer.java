package com.reviewms.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.reviewms.dto.ReviewMessage;
import com.reviewms.review.Review;

import jakarta.inject.Inject;

@Service
public class ReviewMessageProducer {
	@Inject
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(Review review) {
		ReviewMessage reviewMessage = new ReviewMessage();

		reviewMessage.setId(review.getId());
		reviewMessage.setDescription(review.getDescription());
		reviewMessage.setTitle(review.getTitle());
		reviewMessage.setRating(review.getRating());
		reviewMessage.setCompanyId(review.getCompanyId());

		rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
	}

}
