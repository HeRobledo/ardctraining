package com.ardctraining.facades.feedback;


import com.ardctraining.facades.feedback.data.CustomerFeedbackData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;

import java.util.List;

public interface ArdctrainingCustomerFeedbackFacade extends CustomerFacade {

    /**
     *
     * @return
     */
    List<CustomerFeedbackData> getCustomerFeedback();

    /**
     *
     * @param subject
     * @param message
     */
    void saveFeedback(String subject, String message);



}
