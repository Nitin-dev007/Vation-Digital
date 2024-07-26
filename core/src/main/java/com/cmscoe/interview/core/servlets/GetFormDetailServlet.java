package com.cmscoe.interview.core.servlets;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.servlets.post.JSONResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmscoe.interview.core.models.FormDataMapper;
import com.cmscoe.interview.core.models.FormDataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import static com.day.cq.wcm.api.NameConstants.NT_PAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component(service = {Servlet.class})
@SlingServletResourceTypes(resourceTypes = NT_PAGE,
        extensions = "json",
        methods = HttpConstants.METHOD_GET,
        selectors = "getformdata")
@ServiceDescription("Content Fragement Details Servlet")
public class GetFormDetailServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	
	private static String PAGE_PATH = "/content/dam/cmscoeinterview/content-fragement/form-data/jcr:content/data/master"; 
	
	String[] itemsProps = null;
	
	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setContentType(JSONResponse.RESPONSE_CONTENT_TYPE);       
        
		if (Objects.nonNull(PAGE_PATH)) {
			FormDataMapper formDataMapper = new FormDataMapper();
			List<FormDataModel> formDataList = new ArrayList<FormDataModel>();
			List<FormDataModel> formDataModelList = getFormDataDetails(formDataList, request, PAGE_PATH);
			formDataMapper.setFormDataList(formDataModelList);
			String jsonObjectResponse = objectMapper.writeValueAsString(formDataMapper);
			response.getWriter().print(jsonObjectResponse.toString());

		}
       
	}

	private List<FormDataModel> getFormDataDetails(List<FormDataModel> formDataList, SlingHttpServletRequest request, String pagePath) {
		Resource masterResource = request.getResourceResolver().getResource(pagePath);
		ValueMap properties = masterResource.adaptTo(ValueMap.class);
		itemsProps = properties.get("formData", String[].class);
		
		if (Objects.nonNull(itemsProps)) {
			for (String itemsProp : itemsProps) {
				Resource masterResource1 =  request.getResourceResolver().getResource(itemsProp);
				Resource resultResource = request.getResourceResolver().getResource(masterResource1.getPath() + "/jcr:content/data/master");
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
		}
        return formDataList;
	}
}
