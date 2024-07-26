package com.cmscoe.interview.core.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Delete Form Servlet", "sling.servlet.paths=/bin/servlet/delete", 
		"sling.servlet.methods="+HttpConstants.METHOD_GET})
public class DeleteFormServlet  extends SlingSafeMethodsServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DeleteFormServlet.class);

	 @Inject
	 ResourceResolver resolver;
	 
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("col1");
		response.setHeader("Content-Type", "text/html");
		try {
		Session session = request.getResourceResolver().adaptTo(Session.class);
        Node rootNode = session.getNode("/conf/cmscoeinterview/formdata/"+name);
        rootNode.remove();
        session.save();
		}catch (PathNotFoundException e) {
			logger.error("Node not found for deletion at path: /conf/cmscoeinterview/formdata/{}", name, e);
		} catch (RepositoryException e) {
			logger.error("RepositoryException while handling deletion at path: /conf/cmscoeinterview/formdata/{}", name, e);
		} catch (Exception e) {
			logger.error("Unexpected error while handling deletion at path: /conf/cmscoeinterview/formdata/{}", name, e);
		}
		response.getWriter().print(name);
		response.getWriter().close();

	}

}
