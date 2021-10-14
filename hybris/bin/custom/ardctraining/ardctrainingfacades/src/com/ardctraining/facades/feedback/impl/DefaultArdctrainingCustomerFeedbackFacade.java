package com.ardctraining.facades.feedback.impl;

import com.ardctraining.core.feedback.service.CustomerFeedbackService;
import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.facades.feedback.ArdctrainingCustomerFeedbackFacade;
import com.ardctraining.facades.feedback.data.CustomerFeedbackData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DefaultArdctrainingCustomerFeedbackFacade extends DefaultCustomerFacade implements ArdctrainingCustomerFeedbackFacade {

    private CustomerFeedbackService customerFeedbackService;
    private Converter<CustomerFeedbackModel, CustomerFeedbackData> customerFeedbackDataConverter;

    private static final Logger LOG = Logger.getLogger(DefaultArdctrainingCustomerFeedbackFacade.class);

    @Override
    public List<CustomerFeedbackData> getCustomerFeedback() {
        try {
            final UserModel user = getUserService().getCurrentUser();

            if (Objects.nonNull(user) && user instanceof CustomerModel){
                final CustomerModel customer = (CustomerModel) user;
                final List<CustomerFeedbackModel> feedbacks = getCustomerFeedbackService().findFeedbackByCustomer(customer);

                return getCustomerFeedbackDataConverter().convertAll(feedbacks);
            } else {
                LOG.error(String.format("unable to get feedbacks with current user %s", user.getUid()));
            }

        } catch (final UnknownIdentifierException | AmbiguousIdentifierException ex){
            LOG.error(String.format("unable to find feedbacks"),ex);
        }

        return Collections.emptyList();


    }

    @Override
    public void saveFeedback(String subject, String message) {
        try{
            final UserModel user = getUserService().getCurrentUser();

            if(Objects.nonNull(user) && user instanceof CustomerModel){
                final CustomerModel customer = (CustomerModel) user;
                getCustomerFeedbackService().saveFeedback(customer, subject, message);

            } else {
                LOG.error(String.format("Unable to get custom labels with current user %s", user.getUid()));
            }

        }catch (final UnknownIdentifierException | AmbiguousIdentifierException ex){
            LOG.error(String.format("Unable to find product with code %s"), ex);
        }

    }

    public CustomerFeedbackService getCustomerFeedbackService() {
        return customerFeedbackService;
    }

    public void setCustomerFeedbackService(CustomerFeedbackService customerFeedbackService) {
        this.customerFeedbackService = customerFeedbackService;
    }

    public Converter<CustomerFeedbackModel, CustomerFeedbackData> getCustomerFeedbackDataConverter() {
        return customerFeedbackDataConverter;
    }

    public void setCustomerFeedbackDataConverter(Converter<CustomerFeedbackModel, CustomerFeedbackData> customerFeedbackDataConverter) {
        this.customerFeedbackDataConverter = customerFeedbackDataConverter;
    }


}
