package me.dusanov.fa;


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
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class FlightAdvisorApplicationTests {

	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }    
    
    @Test
    public void testHomePage () throws Exception {
	    ResultMatcher ok = MockMvcResultMatchers.status()
	                                            .isOk();
	
	    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/")
	                                                    .contentType(MediaType.TEXT_HTML)
	                                                    .content("<span> hola mundo </span>");
	    this.mockMvc.perform(builder)
	                .andExpect(ok)
	                /* .andDo(MockMvcResultHandlers.print()) */;

    }    
    	
	@Test
	void contextLoads() {
	}

}
