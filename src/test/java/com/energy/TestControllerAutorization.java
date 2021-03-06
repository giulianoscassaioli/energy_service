package com.energy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerAutorization {

	@Autowired
	private MockMvc mockMvc;
	

	@Test
	@WithAnonymousUser
	public void listaComuniWhenUtenteIsAnonymous() throws Exception {
		this.mockMvc.perform(get("/api/comuni")).andExpect(status().isForbidden());
	}

	@Test
	@WithAnonymousUser
	public void testLoginPubblica() throws Exception {
		this.mockMvc.perform(get("/auth/login")).andExpect(status().isMethodNotAllowed());
	}

	
	
	

}
