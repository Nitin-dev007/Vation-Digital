package com.cmscoe.interview.core.models;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class FormDataMapper {

	private List<FormDataModel> formDataList = Collections.emptyList();

	public void setFormDataList(List<FormDataModel> formDataList) {
		this.formDataList = formDataList;
	}

	public List<FormDataModel> getFormDataList() {
		return formDataList;
	}
	
	
	
	
}
