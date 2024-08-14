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
public class CardModel {

    @SlingObject
    SlingHttpServletRequest slingRequest;

    /**
     * The heading.
     */
    @ValueMapValue
    private String heading;

    /**
     * The subheading.
     */
    @ValueMapValue
    private String subheading;

    /**
     * CardList node
     */
    @ChildResource
    @Via("resource")
    private List<CardListModel> cardList;

    public String getHeading() {
        return heading;
    }

    public String getSubheading() {
        return subheading;
    }

    public List<CardListModel> getCardList() {
        return cardList;
    }
    
}
