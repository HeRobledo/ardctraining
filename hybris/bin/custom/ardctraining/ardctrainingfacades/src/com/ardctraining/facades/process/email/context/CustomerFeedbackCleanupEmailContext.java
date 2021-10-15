package com.ardctraining.facades.process.email.context;

import com.ardctraining.core.model.CustomerFeedbackCleanupEmailProcessModel;
import com.ardctraining.facades.constants.ArdctrainingFacadesConstants;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.apache.batik.ext.awt.image.rendered.TileCache.setSize;

public class CustomerFeedbackCleanupEmailContext extends AbstractEmailContext<CustomerFeedbackCleanupEmailProcessModel> {

    private Integer size;
    private Set<Map<String, String>> feedbacks;
    private ConfigurationService configurationService;

    private static final Logger LOG= Logger.getLogger(CustomerFeedbackCleanupEmailContext.class);

    @Override
    public void init(CustomerFeedbackCleanupEmailProcessModel businessProcessModel, EmailPageModel emailPageModel) {
        super.init(businessProcessModel, emailPageModel);

        LOG.info("Entering init method from CustomerFeedbackCleanupEmailContext");
        setSize(businessProcessModel.getCustomerFeedback().size());
        setFeedbacks(businessProcessModel.getCustomerFeedback());
    }

    public void setFeedbacks(Set<String> feedbacks) {
        final Set<Map<String, String>> customerFeedback = new HashSet<>();

        feedbacks.forEach((String s) -> {
            final Map<String, String> feedback = new HashMap<>();
            final String[] parts = s.split("\\|");

            if(parts.length > 0 ) {
                feedback.put("customer", StringUtils.isNotEmpty(parts[0]) ? parts [0] : "*");
                feedback.put("subject", parts[1]);
                feedback.put("message", parts[2]);
                feedback.put("submittedDate", parts[3]);

                customerFeedback.add(feedback);
            }
        });
        this.feedbacks = customerFeedback;
    }

    @Override
    public String getToEmail() {
        return getConfigurationService().getConfiguration().getString(ArdctrainingFacadesConstants.PRODUCT_LABELS_CRONJOB_RECIPIENT_EMAIL);
    }

    @Override
    public String getToDisplayName() {
        return getConfigurationService().getConfiguration().getString(ArdctrainingFacadesConstants.PRODUCT_LABELS_CRONJOB_RECIPIENT_DISPLAYNAME);
    }

    @Override
    protected BaseSiteModel getSite(CustomerFeedbackCleanupEmailProcessModel businessProcessModel) {
        return businessProcessModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(CustomerFeedbackCleanupEmailProcessModel businessProcessModel) {
        return (CustomerModel) businessProcessModel.getUser();
    }

    @Override
    protected LanguageModel getEmailLanguage(CustomerFeedbackCleanupEmailProcessModel businessProcessModel) {
        return businessProcessModel.getLanguage();
    }


}
