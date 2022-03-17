package com.energy;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestIndirizzoController {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaLibriUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/indirizzi")).andExpect(status().isOk());
    }
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaLibriByIdUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/indirizzo/{id}","1")).andExpect(status().isOk());
       
    }
	
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testAddLibro() throws Exception {
		this.mockMvc.perform(post("/salvaindirizzo").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\r\n" 
						+"     \"id\":\"20\",\r\n"
						+ "    \"via\":\"viaTest\",\r\n" 
						+ "    \"civico\":\"202\",\r\n"
						+ "    \"cap\":\"00159\",\r\n"
						+ "    \"localita\":\"mare\"\r\n"
						 + "}")
				.with(csrf())).andExpect(status().isOk());
	}
	
	

}
