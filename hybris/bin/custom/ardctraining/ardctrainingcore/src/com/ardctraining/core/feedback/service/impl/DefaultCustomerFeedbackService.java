package com.ardctraining.core.feedback.service.impl;

import com.ardctraining.core.feedback.dao.CustomerFeedbackDao;
import com.ardctraining.core.feedback.service.CustomerFeedbackService;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.Date;
import java.util.List;

public class DefaultCustomerFeedbackService implements CustomerFeedbackService {

    private CustomerFeedbackDao customerFeedbackDao;
    private ModelService modelService;

    @Override
    public List<CustomerFeedbackModel> findFeedbackByCustomer(final CustomerModel customer) {
        ServicesUtil.validateParameterNotNull(customer, "customer cannot be null");

        return getCustomerFeedbackDao().findFeedbackByCustomer(customer);
    }


    public void saveFeedback(final CustomerModel customer, final String subject, final String message) {
        CustomerFeedbackModel customerFeedbackSave = new CustomerFeedbackModel();
        customerFeedbackSave.setCustomer(customer);
        customerFeedbackSave.setSubject(subject);
        customerFeedbackSave.setMessage(message);
        customerFeedbackSave.setSubmittedDate(new Date());
        getModelService().save(customerFeedbackSave);

    }

    public CustomerFeedbackDao getCustomerFeedbackDao() {
        return customerFeedbackDao;
    }

    public void setCustomerFeedbackDao(CustomerFeedbackDao customerFeedbackDao) {
        this.customerFeedbackDao = customerFeedbackDao;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
