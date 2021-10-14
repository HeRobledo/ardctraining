package com.ardctraining.core.attributehandlers;

import com.ardctraining.core.enums.FeedbackStatusEnum;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import de.hybris.platform.servicelayer.time.TimeService;
import org.apache.commons.lang.BooleanUtils;

import org.joda.time.DateTime;

public class FeedbackStatusAttributeHandler implements DynamicAttributeHandler<FeedbackStatusEnum, CustomerFeedbackModel> {

    private TimeService timeService;
    private ConfigurationService configurationService;


    @Override
    public FeedbackStatusEnum get(CustomerFeedbackModel model) {
        final int days = getConfigurationService().getConfiguration().getInt("feedback.status.calculation.days.threshold");
        DateTime now = new DateTime();
        DateTime dateSubmitted = new DateTime(model.getSubmittedDate());
        DateTime dueDate = dateSubmitted.plusDays(days);

        if(now.isBefore(dueDate)){
            return FeedbackStatusEnum.READ;
        }else if (now.isAfter(dueDate)){
            return FeedbackStatusEnum.READ_PASTDUE;
        }else if((now.isBefore(dueDate) && !model.getRead())){
            return FeedbackStatusEnum.PASTDUE;
        }else{
            return FeedbackStatusEnum.NOT_READ;
        }
    }

    @Override
    public void set(CustomerFeedbackModel model, FeedbackStatusEnum feedbackStatusEnum) {
        throw new UnsupportedOperationException("Write is not valid operation for this dynamic attribute");
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
}
