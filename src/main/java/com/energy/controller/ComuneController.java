package com.energy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energy.model.Comune;
import com.energy.service.ComuneService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class ComuneController {
	
	@Autowired
	ComuneService service;


	@GetMapping("/comuni")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Comune>> findAll(Pageable page){
		return new ResponseEntity<> ( service.getAll(page), HttpStatus.OK);
		
	}
	
	@GetMapping("/comune/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Comune> getComuneById( @PathVariable Long id) {
		return new ResponseEntity<>( service.getById(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/comune/nome/{nome}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Comune> getComuneByNome( @PathVariable String nome) {
		return new ResponseEntity<>( service.getByNome(nome).get(), HttpStatus.OK);
	}
	
    
	
	
}