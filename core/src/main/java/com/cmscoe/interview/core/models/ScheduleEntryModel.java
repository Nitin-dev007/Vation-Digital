package com.cmscoe.interview.core.models;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ScheduleEntryModel {

    /**
     * The session.
     */
    @ValueMapValue
    private String session;

    /**
     * The activity.
     */
    @ValueMapValue
    private String activity;

    /**
     * The trainer.
     */
    @ValueMapValue
    private String trainer;

    public String getSession() {
        return session;
    }

    public String getActivity() {
        return activity;
    }

    public String getTrainer() {
        return trainer;
    }

}
