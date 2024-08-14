package com.cmscoe.interview.core.models;


import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardListModel {

    /**
     * The backgroundImage.
     */
    @ValueMapValue
    private String backgroundImage;

    /**
     * The cardName.
     */
    @ValueMapValue
    private String cardName;

    /**
     * The cardPosition.
     */
    @ValueMapValue
    private String cardPosition;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardPosition() {
        return cardPosition;
    }

    
    
}
