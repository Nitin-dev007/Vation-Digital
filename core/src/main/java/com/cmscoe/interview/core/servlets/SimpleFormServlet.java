package com.cmscoe.interview.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
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


@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Simple Form Servlet", "sling.servlet.paths=/bin/servlet/create", 
		"sling.servlet.methods="+HttpConstants.METHOD_GET})
public class SimpleFormServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Inject
	 ResourceResolver resolver;
	 
	 
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		response.setHeader("Content-Type", "text/html");
		Session session = request.getResourceResolver().adaptTo(Session.class);
		boolean tempVar = true;
		try {			
			String string = "/conf/cmscoeinterview/formdata";
			Resource resultResource = request.getResourceResolver().getResource(string);
			Iterator<Resource> rootNode1 = resultResource.listChildren();
			while (rootNode1.hasNext()) {
				Resource child = rootNode1.next();
				String name1 = child.getName();
				if (name1.equalsIgnoreCase(name)) {
					Node rootNode2 = session.getNode("/conf/cmscoeinterview/formdata/" + name);
					rootNode2.remove();
					Node rootNode = session.getNode("/conf/cmscoeinterview/formdata");
					Node formDataNode = rootNode.addNode(name, "nt:unstructured");
					formDataNode.setProperty("name", name);
					formDataNode.setProperty("email", email);
					formDataNode.setProperty("subject", subject);
					formDataNode.setProperty("message", message);
					tempVar = false;
					break;
				}
			}
			if (tempVar) {
				Node rootNode = session.getNode("/conf/cmscoeinterview/formdata");
				Node formDataNode = rootNode.addNode(name, "nt:unstructured");
				formDataNode.setProperty("name", name);
				formDataNode.setProperty("email", email);
				formDataNode.setProperty("subject", subject);
				formDataNode.setProperty("message", message);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				session.save();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}
		response.getWriter().print(name + email + subject + message);
		response.getWriter().close();
	}

}