package com.cmscoe.interview.core.workflow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.jcr.JcrConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
		service=WorkflowProcess.class,
		property = {"process.label=Update Expire Date property for page"}
		)
public class ExpireDateWorkflowProcess implements WorkflowProcess{

	private static final Logger logger = LoggerFactory.getLogger(ExpireDateWorkflowProcess.class);

	@Reference
	private ResourceResolverFactory factory;
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession wfSession, MetaDataMap metaData) throws WorkflowException {
		
		String payload = workItem.getWorkflowData().getPayload().toString();
		
		try {
			Map<String, Object> props= new HashMap<>();
			props.put(factory.SUBSERVICE, "aemgeeks-service-user");
			ResourceResolver resolver = factory.getServiceResourceResolver(props);
			
			Resource resource = resolver.getResource(payload+"/"+JcrConstants.JCR_CONTENT);
			if (resource != null && payload != null) {
				Node node = resource.adaptTo(Node.class);
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DATE, 20);
				node.setProperty("expirydate", cal);
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				cal.setTime(new Date()); // Using today's date
				cal.add(Calendar.DATE, 30); // Adding 30 days
				String specifiedDate = sdf.format(cal.getTime());
				node.setProperty("deletedate", specifiedDate);
				
				node.getSession().save();				
			}
		} catch (LoginException e) {
            logger.error("LoginException while obtaining resource resolver: {}", e.getMessage(), e);
        } catch (RepositoryException e) {
            logger.error("RepositoryException while setting node properties: {}", e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgumentException occurred: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected exception occurred: {}", e.getMessage(), e);
        }
	}

}
