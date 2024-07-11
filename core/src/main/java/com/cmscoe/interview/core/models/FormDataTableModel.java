package com.cmscoe.interview.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmscoe.interview.core.servlets.GetFormDetailServlet;

import lombok.Getter;

@Model(adaptables = { SlingHttpServletRequest.class, Resource.class } , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL )
@Getter
public class FormDataTableModel {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	SlingHttpServletRequest request;
	
	@Inject
	GetFormDetailServlet getFormDetailServlet;
	
	private static final String PAGE_PATH = "/conf/cmscoeinterview/formdata";
	
	private List<FormDataModel> formDataList =  new ArrayList<>();
	
	public List<FormDataModel> getFormDataList() {
		return formDataList;
	}
	
	@PostConstruct
	public void init() {
		formDataList = getFormDataDetails(formDataList, request, PAGE_PATH);
	}
	
	public List<FormDataModel> getFormDataDetails(List<FormDataModel> formDataList, SlingHttpServletRequest request, String pagePath) {
		 String queryString =
	                "select * FROM [nt:base] as nodes WHERE ISDESCENDANTNODE ([" + pagePath + "])";
	        
	        try {
	            Session session = request.getResourceResolver().adaptTo(Session.class);
	            QueryManager queryManager = session.getWorkspace().getQueryManager();
	            Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
	            QueryResult result = query.execute();
	            NodeIterator nodeIterator = result.getNodes();
				while (nodeIterator.hasNext()) {
					Node resultNode = nodeIterator.nextNode();
					Resource resultResource = request.getResourceResolver().getResource(resultNode.getPath());

					FormDataModel formDataModel = new FormDataModel();
					String name = resultResource.getValueMap().get("name", String.class);
					String email = resultResource.getValueMap().get("email", String.class);
					String message = resultResource.getValueMap().get("message", String.class);
					String subject = resultResource.getValueMap().get("subject", String.class);
					formDataModel.setName(name);
					formDataModel.setEmail(email);
					formDataModel.setMessage(message);
					formDataModel.setSubject(subject);
					formDataList.add(formDataModel);

				}
	        } catch (RepositoryException e) {
	        	logger.error("Exception Occurs In MfeDebugServlet ", e);
	        }
	        return formDataList;
	}

}
