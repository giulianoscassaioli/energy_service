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
public class TestProvinciaController{
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ComuneService comune;
	
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaProvincieUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/api/province")).andExpect(status().isOk());
    }
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaProvincieByIdUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/api/provincia/{id}","1")).andExpect(status().isOk());
       
    }
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaProvincieByNomeUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/api/provincia/nome/{nome}","Roma")).andExpect(status().isOk());
    }

}
