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
public class NavigationModel {

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String email;

	@ValueMapValue
	private String mobNumber;

	@ChildResource(name = "titleList")
	private List<TitleList> titleList = Collections.emptyList();

	@ValueMapValue
	private String buttonText;

	@ValueMapValue
	private String buttonUrl;
	
	@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
	public static class TitleList {
		@ValueMapValue
		private String title;
		@ValueMapValue
		private String url;

		public String getTitle() {
			return title;
		}

		public String getUrl() {
			if (Objects.nonNull(url)) {
				return url + ".html";
			}
			return StringUtils.EMPTY;
		}
	}
}
