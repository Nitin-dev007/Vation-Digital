package com.cmscoe.interview.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class ContactUsModelTest {

	private final AemContext context = new AemContext();
	
	private ContactUsModel contactUsModel;
	
	@BeforeEach
    public void setUp() {
        context.load().json("/component/model/contactus-model.json", "/contactUsModel");
        context.currentResource("/contactUsModel");
        contactUsModel = context.request().adaptTo(ContactUsModel.class);
    }
	
	@Test
    void testGetName() {
        assertEquals("Your Name", contactUsModel.getName());
    }
	
	@Test
    void testGetEmail() {
        assertEquals("Your Email", contactUsModel.getEmail());
    }
	
	@Test
    void testGetSubject() {
        assertEquals("Subject", contactUsModel.getSubject());
    }
	
	@Test
    void testGetMessage() {
        assertEquals("Message", contactUsModel.getMessage());
    }
	
	@Test
    void testGetButtonText() {
        assertEquals("SEND MESSAGE", contactUsModel.getButtonText());
    }
}
