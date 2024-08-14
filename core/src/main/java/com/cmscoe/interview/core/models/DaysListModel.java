package com.cmscoe.interview.core.models;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DaysListModel {

    /**
     * The days.
     */
    @ValueMapValue
    private String days;

    public String getDays() {
        return days;
    }

}
