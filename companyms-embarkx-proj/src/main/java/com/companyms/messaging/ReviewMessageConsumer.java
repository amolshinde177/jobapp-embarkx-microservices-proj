package com.companyms.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.companyms.company.CompanyService;
import com.companyms.dto.ReviewMessage;

import jakarta.inject.Inject;

@Service
public class ReviewMessageConsumer {
	@Inject
	private CompanyService companyService;

	@RabbitListener(queues = "companyRatingQueue")
	public void consumeMessage(ReviewMessage reviewMessage) {
		companyService.updateCompanyRating(reviewMessage);
	}

}
