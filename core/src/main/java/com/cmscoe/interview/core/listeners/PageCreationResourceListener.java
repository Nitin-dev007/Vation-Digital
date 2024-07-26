package com.cmscoe.interview.core.listeners;

import java.util.List;


import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.workflow.WorkflowService;
import com.adobe.granite.workflow.exec.WorkflowData;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.LoginException;

@Component(service = ResourceChangeListener.class,
           property = {
               ResourceChangeListener.PATHS + "=/content/cmscoeinterview/us/en"
           })
public class PageCreationResourceListener implements ResourceChangeListener {

    private static final Logger log = LoggerFactory.getLogger(PageCreationResourceListener.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private WorkflowService workflowService;

    @Override
    public void onChange(List<ResourceChange> changes) {
        for (ResourceChange change : changes) {
            if (change.getType() == ResourceChange.ChangeType.ADDED) {
                String path = change.getPath();
                Map<String, Object> param = new HashMap<>();
            param.put(ResourceResolverFactory.SUBSERVICE, "aemgeeks-service-user");
                try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param)) {
                    Resource resource = resourceResolver.getResource(path);
                    if (resource != null) {
                        log.info("Page created at: {}", path);
                        // Trigger the workflow
                        startWorkflow(resourceResolver, path);
                    }
                } catch (Exception e) {
                    log.error("Unexpected error while handling page creation event at: {}", path, e);
                }
            }
        }
    }

    private void startWorkflow(ResourceResolver resourceResolver, String path) {
        WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
        try {
            WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/update-expire-date");
            if (workflowModel != null) {
                WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", path);
                workflowSession.startWorkflow(workflowModel, workflowData);
                log.info("Workflow started for path from Lisener: {}", path);
            } else {
                log.warn("Workflow model not found at: /var/workflow/models/update-expire-date");
            }
        } catch (WorkflowException e) {
            log.error("Error starting workflow for path: {}", path, e);
        }
    }
}