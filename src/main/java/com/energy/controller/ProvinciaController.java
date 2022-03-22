package com.energy.controller;

import java.util.Optional;

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

import com.energy.model.Indirizzo;
import com.energy.model.Provincia;
import com.energy.service.ProvinciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class ProvinciaController {

	@Autowired
	ProvinciaService service;

	@GetMapping("/province")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Provincia>> findAll(Pageable page){
		return new ResponseEntity<> ( service.getAll(page), HttpStatus.OK);
		
	}
	
	@GetMapping("/provincia/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@Operation(summary = "EndPoint per ricercare un provincia dato il suo id; Per questo e tutti gli altri EndPoint"
			+ " con paginazione lasciare l'elemento sort come una stringa vuota se non si vuole un particolare ordinamento"
			+ " oppure inserire un sort valido")
	public ResponseEntity<Provincia> getProvinciaById( @PathVariable Long id) {
		return new ResponseEntity<>( service.getById(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/provincia/nome/{nome}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Provincia> getProvinciaByNome( @PathVariable String nome) {
		return new ResponseEntity<>( service.getByNome(nome).get(), HttpStatus.OK);
	}
	


	
}
