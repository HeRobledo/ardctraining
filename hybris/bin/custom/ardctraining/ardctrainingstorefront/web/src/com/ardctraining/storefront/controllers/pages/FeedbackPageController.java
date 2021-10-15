package com.ardctraining.storefront.controllers.pages;

import com.ardctraining.core.feedback.service.CustomerFeedbackService;
import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.facades.feedback.ArdctrainingCustomerFeedbackFacade;
import com.ardctraining.facades.feedback.data.CustomerFeedbackData;
import com.ardctraining.storefront.controllers.ControllerConstants;
import com.ardctraining.storefront.form.FeedbackForm;
import com.ardctraining.storefront.form.validation.ArdctrainingFeedbackValidator;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import org.apache.commons.lang.BooleanUtils;
import org.drools.core.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/feedback")
public class FeedbackPageController extends AbstractPageController {

    @Resource(name="feedbackFacade")
    private ArdctrainingCustomerFeedbackFacade ardctrainingCustomerFeedbackFacade;

    @Resource
    private ArdctrainingFeedbackValidator ardctrainingFeedbackValidator;


    private static final String FEEDBACK_PAGE_LABEL = "feedback";
    private static final String FEEDBACK_PAGE_LIST = "feedbacks";
    private static final String FEEDBACK_FORM_ATTR = "feedbackform";

    @GetMapping
    public String getFeedbackPageView(final Model model) throws CMSItemNotFoundException {
        final ContentPageModel contentPageModel = getContentPageForLabelOrId(FEEDBACK_PAGE_LABEL);
        storeCmsPageInModel(model, contentPageModel);
        setUpMetaDataForContentPage(model, contentPageModel);

        model.addAttribute(FEEDBACK_FORM_ATTR, createEmptyForm());
        model.addAttribute(FEEDBACK_PAGE_LIST, ardctrainingCustomerFeedbackFacade.getCustomerFeedback());
        return ControllerConstants.Views.Pages.Feedback.FeedbackPage;
    }

    @PostMapping("/save")
    public String submitFeedback(final Model model,
                                 final FeedbackForm feedbackform,
                                 final BindingResult bindingResult){
        ardctrainingFeedbackValidator.validate(feedbackform, bindingResult);
        if(BooleanUtils.isFalse(bindingResult.hasErrors())){
            String subject, message;
            subject = feedbackform.getSubject();
            message = feedbackform.getMessage();
            ardctrainingCustomerFeedbackFacade.saveFeedback(subject, message);

        }
        return REDIRECT_PREFIX + FEEDBACK_PAGE_LABEL;
    }

    private FeedbackForm createEmptyForm(){
        final FeedbackForm feedbackform = new FeedbackForm();
        feedbackform.setSubject(StringUtils.EMPTY);
        feedbackform.setMessage(StringUtils.EMPTY);

        return feedbackform;
    }
}
