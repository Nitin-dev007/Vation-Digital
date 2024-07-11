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

@Component(
		service=WorkflowProcess.class,
		property = {"process.label=Update Expire Date property for page"}
		)
public class ExpireDateWorkflowProcess implements WorkflowProcess{

	@Reference
	private ResourceResolverFactory factory;
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession wfSession, MetaDataMap metaData) throws WorkflowException {
		
		String payload = workItem.getWorkflowData().getPayload().toString();
		
		try {
			Map<String, Object> props= new HashMap<>();
			props.put(factory.SUBSERVICE, "writeservice");
			ResourceResolver resolver = factory.getServiceResourceResolver(props);
			
			Resource resource = resolver.getResource(payload+"/jcr:content");
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
		} catch (LoginException | RepositoryException e) {
			e.printStackTrace();
		}
		
	}

}
