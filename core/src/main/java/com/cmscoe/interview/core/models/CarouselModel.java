package com.cmscoe.interview.core.models;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/**
 * Sling Model for Carousel Component.
 * (/apps/cmscoeinterview/components/carouselComponent)
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselModel {

    @SlingObject
    SlingHttpServletRequest slingRequest;

    /**
     * The title.
     */
    @ValueMapValue
    private String title;

    /**
     * The joinUs.
     */
    @ValueMapValue
    private String joinUs;

    /**
     * The contactUs.
     */
    @ValueMapValue
    private String contactUs;

    /**
     * carouselList node
     */
    @ChildResource
    @Via("resource")
    private List<CarouselListModel> carouselList;

    public String getTitle() {
        return title;
    }

    public String getJoinUs() {
        return joinUs;
    }

    public String getContactUs() {
        return contactUs;
    }

    public List<CarouselListModel> getCarouselList() {
        return carouselList;
    }
    
}
