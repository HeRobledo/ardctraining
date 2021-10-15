package com.ardctraining.backoffice.labels;

import com.ardctraining.core.model.CustomerFeedbackModel;
import com.hybris.cockpitng.labels.LabelProvider;

import java.util.Objects;

public class CustomerFeedbackLabel implements LabelProvider<CustomerFeedbackModel> {

    private static final String ALL_WILDCARD="*";

    @Override
    public String getLabel(CustomerFeedbackModel customerFeedbackModel) {
        final String customer = Objects.nonNull(customerFeedbackModel.getCustomer()) ?
                customerFeedbackModel.getCustomer().getUid() : ALL_WILDCARD;
        return new StringBuilder()
                .append(customerFeedbackModel.getSubject())
                .append(" - ")
                .append(customer).toString();
    }

    @Override
    public String getDescription(CustomerFeedbackModel customerFeedbackModel) {
        return getLabel(customerFeedbackModel);
    }

    @Override
    public String getIconPath(CustomerFeedbackModel customerFeedbackModel) {
        return null;
    }
}
