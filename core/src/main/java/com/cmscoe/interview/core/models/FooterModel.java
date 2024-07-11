package com.cmscoe.interview.core.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import lombok.Getter;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class } , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL )
@Getter
public class FooterModel {

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String address;
	
	@ValueMapValue
	private String email;

	@ValueMapValue
	private String mobNumber;
	
	@ValueMapValue
	private String newsletterTitle;
	
	@ValueMapValue
	private String newsletterDescription;
	
	@ChildResource(name = "quickLinksList")
	private List<QuickLinksList> quickLinksList = Collections.emptyList();
	
	@ChildResource(name = "popularLinksList")
	private List<PopularLinksList> popularLinksList = Collections.emptyList();
	
	@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
	public static class PopularLinksList {
		@ValueMapValue
		private String popularLinksTitle;
		@ValueMapValue
		private String popularLinksUrl;
		public String getPopularLinksTitle() {
			return popularLinksTitle;
		}

		public String getPopularLinksUrl() {
			if (Objects.nonNull(popularLinksUrl)) {
				return popularLinksUrl + ".html";
			}
			return StringUtils.EMPTY;
		}
	}
	
	@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
	public static class QuickLinksList {
		@ValueMapValue
		private String quickLinksTitle;
		@ValueMapValue
		private String quickLinksUrl;

		public String getQuickLinksTitle() {
			return quickLinksTitle;
		}

		public String getQuickLinksUrl() {
			if (Objects.nonNull(quickLinksUrl)) {
				return quickLinksUrl + ".html";
			}
			return StringUtils.EMPTY;
		}

	}

	public String getTitle() {
		return title;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getMobNumber() {
		return mobNumber;
	}

	public String getNewsletterTitle() {
		return newsletterTitle;
	}

	public String getNewsletterDescription() {
		return newsletterDescription;
	}
}
