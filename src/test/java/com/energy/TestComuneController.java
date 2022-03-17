package com.energy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.energy.service.ComuneService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestComuneController {


	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ComuneService comune;
	
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaComuniUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/comuni")).andExpect(status().isOk());
    }
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaComuneByIdUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/comune/{id}","1")).andExpect(status().isOk());
       
    }
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaComuneByNomeUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/comune/nome/{nome}","Roma")).andExpect(status().isOk());
    }
	

}
