package com.cmscoe.interview.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class } , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL )
public class ContactUsModel {

	@ValueMapValue
	private String name;

	@ValueMapValue
	private String email;

	@ValueMapValue
	private String subject;
	
	@ValueMapValue
	private String message;
	
	@ValueMapValue
	private String buttonText;

	@ValueMapValue
	private String buttonUrl;

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public String getButtonText() {
		return buttonText;
	}

	public String getButtonUrl() {
		return buttonUrl;
	}
	
	
}
