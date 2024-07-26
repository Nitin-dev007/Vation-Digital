package com.cmscoe.interview.core.models;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Getter
@Setter
public class FormDataModel {

	@ValueMapValue
    private String name;
    @ValueMapValue
    private String email;
    @ValueMapValue
    private String message;
    @ValueMapValue
    private String subject;

	private static final List<FormDataModel> formDataList = new ArrayList<>();

	// Add a new entry to the list
    public void addToList(FormDataModel formData) {
        synchronized (formDataList) {
            formDataList.add(formData);
        }
    }

	// Get a copy of the list
    public List<FormDataModel> getFormDataList() {
        synchronized (formDataList) {
            return new ArrayList<>(formDataList);
        }
    }

	// Clear the list (if needed)
    public void clearFormDataList() {
        synchronized (formDataList) {
            formDataList.clear();
        }
    }
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
    
}
