package me.dusanov.fa.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class CityControllerTests {

	private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJST0xFX1VTRVIiLCJleHAiOjE2MDMyMDExNTd9.4CrfeplRz3yuj0iEO1lS4WFL4rTdigvEpZ-lHEU8WPJ4LgHDRdqgIvwi_q4J8xKNOqKW4MBWOsNsYE2BnfdVQA";

	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.apply(springSecurity()).build();
    }    
    
    @Test
    @Transactional
    //@Disabled
//    @WithMockUser(username="user")//,"admin"
    public void testAddNewCityHappyPath () throws Exception {
	    ResultMatcher ok = MockMvcResultMatchers.status().isForbidden();
	                                            //.isOk();
	
	    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/cities")
	                                                    .contentType(MediaType.APPLICATION_JSON)
	                                                    //.header("Authorization", TOKEN)
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
	                                                    .header("Authorization", TOKEN)
	                                                    .content("{\r\n" + 
	                                                    		"    \"name\": \" all is love \",\r\n" + 
	                                                    		"    \"country\": \"no country\",\r\n" + 
	                                                    		"    \"description\": \"\"\r\n" + 
	                                                    		"}");
	    this.mockMvc.perform(builder)
	                .andExpect(notok);

    }
    
    @Test
    @Transactional
    public void testAddNewCitySadPathNotAuth () throws Exception {
	    ResultMatcher notok = MockMvcResultMatchers.status().isForbidden();
	    
	    //make a req with a missing description for teh city
	    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/cities")
	                                                    .contentType(MediaType.APPLICATION_JSON)
	                                        //            .header("Authorization", TOKEN)
	                                                    .content("{\r\n" + 
	                                                    		"    \"name\": \" all is love \",\r\n" + 
	                                                    		"    \"country\": \"no country\",\r\n" + 
	                                                    		"    \"description\": \"\"\r\n" + 
	                                                    		"}");
	    this.mockMvc.perform(builder)
	                .andExpect(notok);

    }    
    
    @Test
    @WithMockUser("user")
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