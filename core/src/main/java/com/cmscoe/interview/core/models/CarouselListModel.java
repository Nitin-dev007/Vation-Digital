package com.cmscoe.interview.core.models;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselListModel {

    /**
     * The backgroundImage.
     */
    @ValueMapValue
    private String backgroundImage;

    /**
     * The carouselDescription.
     */
    @ValueMapValue
    private String carouselDescription;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getCarouselDescription() {
        return carouselDescription;
    }

}
