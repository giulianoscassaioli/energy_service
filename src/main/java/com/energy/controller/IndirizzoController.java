package com.energy.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energy.model.Indirizzo;
import com.energy.service.IndirizzoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class IndirizzoController {

	@Autowired
	IndirizzoService service;

	@PostMapping("/salvaindirizzo")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String save(@RequestBody Indirizzo c) {
		service.save(c);
		return "Indirizzo salvato con successo";

	}

	@DeleteMapping("/eliminaindirizzo/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> eliminaIndirizzo(@PathVariable(required = true) Long id) {
		Optional<Indirizzo> i= service.getIndirizzoById(id); 
		if(i.isPresent()){
			service.deleteById(id);
			return new ResponseEntity<>("Indirizzo eliminato con successo", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("L'indirizzo con questo id non esiste!", HttpStatus.BAD_REQUEST);
			
		}
	}

	@GetMapping("/indirizzo/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> getIndirizzoById(@PathVariable Long id) {
		Optional<Indirizzo> i= service.getIndirizzoById(id); 
		if(i.isPresent()){
		return new ResponseEntity<>(i.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("L'indirizzo con questo id non esiste!", HttpStatus.OK);
			
		}
	}

	@GetMapping("/indirizzo/via/{via}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> getIndirizzoByVia(@PathVariable String via, Pageable page) {
		Page<Indirizzo> i= service.getIndirizzoByVia(via, page); 
		if(i.hasContent()){
		return new ResponseEntity<>(i, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Questa via non esite!", HttpStatus.OK);
			
		}
		
	}

	@GetMapping("/indirizzi")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Indirizzo>> getAllIndirizzi(Pageable page) {
		return new ResponseEntity<>(service.getAllIndirizzi(page), HttpStatus.OK);

	}

	@PutMapping("/aggiornaindirizzo/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String aggiornaIndirizzo(@PathVariable(required = true) Long id, @RequestBody Indirizzo c) {
		service.update(id, c);
		return "Indirizzo aggiornato";
	}
}