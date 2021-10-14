package com.ardctraining.core.feedback.dao;

import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface CustomerFeedbackDao {

    /**
     *  find Feedback customer
     * @param customer
     * @return
     */
    List<CustomerFeedbackModel> findFeedbackByCustomer(CustomerModel customer);

}
