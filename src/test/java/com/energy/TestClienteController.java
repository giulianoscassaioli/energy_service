package com.energy;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.energy.model.Cliente;
import com.energy.model.Indirizzo;
import com.energy.service.ComuneService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TestClienteController {


	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ComuneService comune;
	
	Indirizzo ind;
	
	Cliente cli;
	

	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaAutoriUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/trovatutticlienti")).andExpect(status().isOk());
    }
	
	@Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void listaAutoreByIdUtenteAutenticato() throws Exception {
        this.mockMvc.perform(get("/trovacliente/id/{id}","1")).andExpect(status().isOk());
       
    }
	
	
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testAddCliente() throws Exception {
		this.mockMvc.perform(post("/salvacliente").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("{\r\n"
						+ "  \"id\":0,\r\n"
						+ "  \"ragioneSociale\": \"string1\",\r\n"
						+ "  \"partitaIva\": \"string2\",\r\n"
						+ "  \"tipoCliente\": \"SRL\",\r\n"
						+ "  \"email\": \"string1@email.com\",\r\n"
						+ "  \"pec\": \"string2@pec.com\",\r\n"
						+ "  \"telefono\": \"064599665\",\r\n"
						+ "  \"nomeContatto\": \"string\",\r\n"
						+ "  \"cognomeContatto\": \"string\",\r\n"
						+ "  \"telefonoContatto\": \"string\",\r\n"
						+ "  \"emailContatto\": \"string\",\r\n"
						+ "  \"indirizzoSedeOperativa\": {\r\n"
						+ "    \"id\":16,\r\n"
						+ "    \"via\": \"string\",\r\n"
						+ "    \"civico\": 0,\r\n"
						+ "    \"cap\": \"string\",\r\n"
						+ "    \"localita\": \"string\"\r\n"
						+ "  },\r\n"
						+ "  \"indirizzoSedeLegale\": {\r\n"
						+ "    \"id\":17,\r\n"
						+ "    \"via\": \"string\",\r\n"
						+ "    \"civico\": 0,\r\n"
						+ "    \"cap\": \"string\",\r\n"
						+ "    \"localita\": \"string\"\r\n"
						+ "  },\r\n"
						+ "  \"dataInserimento\": \"2022-03-12\",\r\n"
						+ "  \"dataUltimoContatto\": \"2022-03-12\",\r\n"
						+ "  \"fatturatoAnnuale\": 0\r\n"
						+ "  \r\n"
						+ "}")
				.with(csrf())).andExpect(status().isOk());
		
	}
	
	
}
