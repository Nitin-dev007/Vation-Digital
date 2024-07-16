package com.cmscoe.interview.core.schedulers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(
		service=Runnable.class,
		property = {"scheduler.expression=*/10 * * * * ?"}
		)
public class ExpiredPage implements Runnable{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	private ResourceResolverFactory factory;
	@Reference
	private Replicator replicator;
	@Override
	public void run() {
		logger.info("Scheduler is executing every after 10 sec");
		
		String customPages ="/content/cmscoeinterview/us/en";
		
		Map<String, Object> props= new HashMap<>();
		props.put(factory.SUBSERVICE, "aemgeeks-service-user");
		
		try {
			ResourceResolver resolver = factory.getServiceResourceResolver(props);
			PageManager pageManager = resolver.adaptTo(PageManager.class);
			Page homePage = pageManager.getPage(customPages);
			
			if (customPages != null) {
				Iterator<Page> childPage = homePage.listChildren();
				while(childPage.hasNext()) {
					Page page = childPage.next();
					ValueMap pageProps = page.getProperties();
					Calendar offTime = pageProps.get("offTime", Calendar.class);
					String pageStatus = pageProps.get("cq:lastReplicationAction", String.class);
					Date currentDate = new Date();
					logger.info("page url - {}", page.getPath());
					if (offTime != null && pageStatus!=null && pageStatus.equals("Activate") && currentDate.compareTo(offTime.getTime()) > 0) {
						logger.info("date compare - {}", currentDate.compareTo(offTime.getTime()));
						logger.info("page url got deactivated - {}", page.getPath());
						replicator.replicate(resolver.adaptTo(Session.class), ReplicationActionType.DEACTIVATE, page.getPath());
					}
				}
			}
			
		} catch (LoginException | ReplicationException e) {
			logger.error("Error while deactivating pages {}", e.getMessage());
			e.printStackTrace();
		}
		
		
	}

}
