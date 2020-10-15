package me.dusanov.fa.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class CityControllerTests {

	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }    
    
    @Test
    @Transactional
    public void testAddNewCityHappyPath () throws Exception {
	    ResultMatcher ok = MockMvcResultMatchers.status()
	                                            .isOk();
	
	    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/cities")
	                                                    .contentType(MediaType.APPLICATION_JSON)
	                                                    .content("{\r\n" + 
	                                                    		"    \"name\": \" all is love \",\r\n" + 
	                                                    		"    \"country\": \"no country\",\r\n" + 
	                                                    		"    \"description\": \"some lame description\"\r\n" + 
	                                                    		"}");
	    this.mockMvc.perform(builder)
	                .andExpect(ok);

    }	
    
    @Test
    @Transactional
    public void testAddNewCitySadPath () throws Exception {
	    ResultMatcher notok = MockMvcResultMatchers.status()
	                                            .isBadRequest();
	    
	    //make a req with a missing description for teh city
	    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/cities")
	                                                    .contentType(MediaType.APPLICATION_JSON)
	                                                    .content("{\r\n" + 
	                                                    		"    \"name\": \" all is love \",\r\n" + 
	                                                    		"    \"country\": \"no country\",\r\n" + 
	                                                    		"    \"description\": \"\"\r\n" + 
	                                                    		"}");
	    this.mockMvc.perform(builder)
	                .andExpect(notok);

    }    
    
    @Test
    public void testSearch() throws Exception {
    	ResultMatcher ok = MockMvcResultMatchers.status()
                .isOk();
    	
    	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/cities/search/el cal")
    												/*.contentType(MediaType.APPLICATION_JSON) 
									                .content("")*/;
    	
    	this.mockMvc.perform(builder)
    				.andExpect(ok)
    				.andExpect(jsonPath("$[0].name", containsString("el calafate")));
    }
	
}