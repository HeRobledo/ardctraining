package com.ardctraining.core.feedback.service;

import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface CustomerFeedbackService {

    /**
     *  find Feedback by customer
     * @param customer
     * @return
     */
    List<CustomerFeedbackModel> findFeedbackByCustomer(CustomerModel customer);

    /**
     *  save feedback
     * @param customer
     * @param subject
     * @param message
     * @return
     */
    void saveFeedback(CustomerModel customer, String subject, String message);

}
