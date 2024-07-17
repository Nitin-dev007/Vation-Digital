package com.cmscoe.interview.core.config;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Cron Job Scheduler")
public @interface CronJob {

  @AttributeDefinition(name = "Cron Scheduler Expression")
  String scheduler_expression() default "*/10 * * * * ?";

}