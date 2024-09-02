package com.cmscoe.interview.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ContactUsModel {

    @SlingObject
    SlingHttpServletRequest slingRequest;

    /**
     * The title.
     */
    @ValueMapValue
    private String name;

    /**
     * The email.
     */
    @ValueMapValue
    private String email;

    /**
     * The mobileNumber.
     */
    @ValueMapValue
    private String subject;

    /**
     * The mobileNumber.
     */
    @ValueMapValue
    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * The buttonText.
     */
    @ValueMapValue
    private String buttonText;

    /**
     * The buttonUrl.
     */
    @ValueMapValue
    private String buttonUrl;

    public SlingHttpServletRequest getSlingRequest() {
        return slingRequest;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    }
