package com.energy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energy.model.Utente;
import com.energy.service.UtenteService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@GetMapping(path = "/utente")
	public ResponseEntity<List<Utente>> findAll() {
		List<Utente> findAll = utenteService.findAll();

		if (!findAll.isEmpty()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/utente/{id}")
	public ResponseEntity<?> findById(@PathVariable(required = true) Long id) {
		Optional<Utente> find = utenteService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("L'utente con questo id non esiste!", HttpStatus.NOT_FOUND);
		}

	}


	@DeleteMapping(path = "/utente/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Utente> l= utenteService.findById(id);
		if(l.isPresent()){
			utenteService.delete(id);
			return new ResponseEntity<>("Utente cancellato", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("L'utente con questo id non esiste!", HttpStatus.NOT_FOUND);


	}

}
