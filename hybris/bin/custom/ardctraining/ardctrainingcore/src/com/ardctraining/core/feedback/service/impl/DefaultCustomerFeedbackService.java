package com.ardctraining.core.feedback.service.impl;

import com.ardctraining.core.feedback.dao.CustomerFeedbackDao;
import com.ardctraining.core.feedback.service.CustomerFeedbackService;
import com.ardctraining.core.job.CustomProductLabelCleanupJobPerformable;
import com.ardctraining.core.model.CustomProductLabelCleanupEmailProcessModel;
import com.ardctraining.core.model.CustomerFeedbackCleanupEmailProcessModel;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.ModelRemovalException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.site.BaseSiteService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DefaultCustomerFeedbackService implements CustomerFeedbackService {

    private CustomerFeedbackDao customerFeedbackDao;
    private ModelService modelService;

    private BusinessProcessService businessProcessService;
    private TimeService timeService;
    private BaseSiteService baseSiteService;

    private static final String FIELD_SEPARATOR = "|";
    private static final String ELECTRONICS_SITE = "electronics";
    private static final String CUSTOMER_FEEDBACK_EMAIL_CLEANUP_PROCESS = "customerFeedbackCleanupEmailProcess";

    private static final Logger LOG = Logger.getLogger(DefaultCustomerFeedbackService.class);

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

        try {
            final Set<String> feedbacks = new HashSet<>();
            final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss.S");
            final Date now = getTimeService().getCurrentTime();
            final CustomerFeedbackCleanupEmailProcessModel process = getBusinessProcessService().
                    createProcess(new StringBuilder()
                            .append(CUSTOMER_FEEDBACK_EMAIL_CLEANUP_PROCESS)
                            .append("-")
                            .append(dateFormat.format(now)).toString(),"customerFeedbackCleanupEmailProcess");

            process.setLanguage(getBaseSiteService().getCurrentBaseSite().getDefaultLanguage());
            process.setCustomerFeedback(feedbacks);
            process.setSite(getBaseSiteService().getBaseSiteForUID(ELECTRONICS_SITE));

            getModelService().save(process);
            getBusinessProcessService().startProcess(process);

        } catch (final ModelRemovalException ex) {
            LOG.error("Error with Business Process", ex);

        }
    }


    private String getCustomerFeedback(CustomerFeedbackModel customerFeedbackModel){
        return new StringBuilder()
                .append(Objects.isNull(customerFeedbackModel.getCustomer().getUid()) ? StringUtils.EMPTY : customerFeedbackModel.getCustomer().getUid())
                .append(FIELD_SEPARATOR)
                .append(customerFeedbackModel.getSubject())
                .append(FIELD_SEPARATOR)
                .append(customerFeedbackModel.getMessage())
                .append(FIELD_SEPARATOR)
                .append(customerFeedbackModel.getSubmittedDate())
                .toString();
    }

    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    public BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
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
