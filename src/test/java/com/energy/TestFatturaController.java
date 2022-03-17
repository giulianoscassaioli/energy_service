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
public class TestFatturaController {
	

	@Autowired
	private MockMvc mockMvc;
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaFattureUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/api/allfatture")).andExpect(status().isOk());
    }
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaFattureByAnnoUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/api/ricercaperanno/{anno}","2020")).andExpect(status().isOk());
       
    }
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testAddFattura() throws Exception{
		this.mockMvc.perform(post("/api/salvafattura").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\r\n"
						+ "  \"id\": 0,\r\n"
						+ "  \"dataFattura\": \"2022-03-12\",\r\n"
						+ "  \"numero\": 0,\r\n"
						+ "  \"anno\": 0,\r\n"
						+ "  \"importo\": 0,\r\n"
						+ "  \"statoFattura\": \"string\"\r\n"
						+ "}")
				
				.with(csrf())).andExpect(status().isOk());
	}

}
