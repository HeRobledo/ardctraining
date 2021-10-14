package com.ardctraining.storefront.form.validation;

import com.ardctraining.storefront.form.FeedbackForm;

import org.apache.solr.common.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "ardctrainingfeedbackValidator")
public class ArdctrainingFeedbackValidator {


    public void validate(final Object object, final Errors errors){
        final FeedbackForm form = (FeedbackForm) object;

        validateSubjuect(errors, form.getSubject());
        validateMessage(errors, form.getMessage());
    }

    private void validateSubjuect(final Errors errors, final String subject){
        if(StringUtils.isEmpty(subject)){
            errors.rejectValue("subject", "feedback.subject.invalid");
        }
    }

    private void validateMessage(final Errors errors, final String message){
        if(StringUtils.isEmpty(message)){
            errors.rejectValue("message", "feedback.subject.invalid");
        }
    }

    public boolean supports(Class<?> aClass){
        return FeedbackForm.class.equals(aClass);

    }

}
