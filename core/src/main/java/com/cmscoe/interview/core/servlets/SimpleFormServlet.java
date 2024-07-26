package com.cmscoe.interview.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;


@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Simple Form Servlet", "sling.servlet.paths=/bin/servlet/create", 
		"sling.servlet.methods="+HttpConstants.METHOD_GET})
public class SimpleFormServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SimpleFormServlet.class);


	private static final String FORM_DATA_PATH = "/conf/cmscoeinterview/formdata";
	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_EMAIL = "email";
	private static final String PROPERTY_SUBJECT = "subject";
	private static final String PROPERTY_MESSAGE = "message";

	 @Inject
	 ResourceResolver resolver;
	 
	 
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter(PROPERTY_NAME);
		String email = request.getParameter(PROPERTY_EMAIL);
		String subject = request.getParameter(PROPERTY_SUBJECT);
		String message = request.getParameter(PROPERTY_MESSAGE);
		response.setHeader("Content-Type", "text/html");
		Session session = request.getResourceResolver().adaptTo(Session.class);
		boolean tempVar = true;
		try {			
			String string = FORM_DATA_PATH;
			Resource resultResource = request.getResourceResolver().getResource(string);
			Iterator<Resource> rootNode1 = resultResource.listChildren();
			while (rootNode1.hasNext()) {
				Resource child = rootNode1.next();
				String name1 = child.getName();
				if (name1.equalsIgnoreCase(name)) {
					Node rootNode2 = session.getNode(FORM_DATA_PATH + name);
					rootNode2.remove();
					Node rootNode = session.getNode(FORM_DATA_PATH);
					Node formDataNode = rootNode.addNode(name, JcrConstants.NT_UNSTRUCTURED);
					formDataNode.setProperty(PROPERTY_NAME, name);
					formDataNode.setProperty(PROPERTY_EMAIL, email);
					formDataNode.setProperty(PROPERTY_SUBJECT, subject);
					formDataNode.setProperty(PROPERTY_MESSAGE, message);
					tempVar = false;
					break;
				}
			}
			if (tempVar) {
				Node rootNode = session.getNode(FORM_DATA_PATH);
				Node formDataNode = rootNode.addNode(name, JcrConstants.NT_UNSTRUCTURED);
				formDataNode.setProperty(PROPERTY_NAME, name);
				formDataNode.setProperty(PROPERTY_EMAIL, email);
				formDataNode.setProperty(PROPERTY_SUBJECT, subject);
				formDataNode.setProperty(PROPERTY_MESSAGE, message);
			}
			
		} catch (PathNotFoundException e) {
			logger.error("PathNotFoundException while handling form data creation for name: {}", name, e);
		} catch (ValueFormatException e) {
			logger.error("ValueFormatException while handling form data creation for name: {}", name, e);
		} catch (RepositoryException e) {
			logger.error("RepositoryException while handling form data creation for name: {}", name, e);
		} catch (Exception e) {
			logger.error("Unexpected error while handling form data creation for name: {}", name, e);
		}  finally {
			try {
				session.save();
			} catch (RepositoryException e) {
				logger.error("RepositoryException while saving session", e);
			}
		}
		response.getWriter().print(name + email + subject + message);
		response.getWriter().close();
	}

}