package com.energy.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
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

import com.energy.model.Cliente;
import com.energy.model.Fattura;
import com.energy.service.FatturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class FatturaController {

	@Autowired
	FatturaService service;

	@PostMapping("/salvafattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Endpoint per salvare un nuova fattura ;prima di utlizzare eliminare gli array delle fatture"
			+ " contenuti nel cliente; eliminare le provincia al interno del comune,ed i comuni al interno degli indirizzi"
			+ " ;utilizzare un corretto id cliente con la sua rispettiva ragiona sociale")
	public ResponseEntity<?> save(@RequestBody Fattura c) {
		Optional<Fattura> cc = service.findById(c.getId());
		if (!cc.isPresent()) {
			Fattura save = service.save(c);
			return new ResponseEntity<>(save, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("La Fattura con questo id esiste gia", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminafattura/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> eliminaFattura(@PathVariable(required = true) Long id) {
		Optional<Fattura> cc = service.findById(id);
		if (cc.isPresent()) {
			service.deleteById(id);
			return new ResponseEntity<>("Fattura cancellata!", HttpStatus.OK);
		}
		return new ResponseEntity<>("La fattura con questo id non esiste!", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/aggiornafattura/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Operation(summary = "Endpoint per aggiornare una fattura ;prima di utlizzare eliminare gli array delle fatture"
			+ " contenuti nel cliente; eliminare le provincia al interno del comune"
			+ " ;utilizzare un corretto id cliente con la sua rispettiva ragiona sociale")
	public ResponseEntity<Fattura> aggiornaFattura(@PathVariable(required = true) Long id, @RequestBody Fattura c) {
		Fattura save = service.update(id, c);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
	@GetMapping("/ricercapercliente/{cliente}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> findByCliente(@PathVariable String cliente, Pageable page) {
		Page<Fattura> list = service.findByCliente(cliente, page);
		if (list.hasContent()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>("Non cè nessun cliente con quella ragione sociale!", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/ricercaperstato/{stato}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@Operation(summary = "EndPoint per ricercare un fattura dal sua stato; Per questo e tutti gli altri EndPoint"
			+ " con paginazione lasciare l'elemento sort come una stringa vuota se non si vuole un particolare ordinamento"
			+ " oppure inserire un sort valido")
	public ResponseEntity<?> findByStato(@PathVariable String stato, Pageable page) {
		Page<Fattura> list = service.findByStato(stato, page);
		if (list.hasContent()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>("Non cè nessuna fattura con questo stato!", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/ricercaperanno/{anno}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> findByAnno(@PathVariable Integer anno, Pageable page) {
		Page<Fattura> list = service.findByAnno(anno, page);
		if (list.hasContent()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>("Non cè nessuna fattura con questo anno!", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/ricercaperdata/{dataFattura}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> findByData(Pageable page,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFattura) {
		Page<Fattura> list = service.findByData(dataFattura, page);
		if (list.hasContent()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>("Non cè nessuna fattura fatta in questa data!", HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/ricercaperrange/{minimo}/{massimo}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> findByRange(@PathVariable BigDecimal minimo, @PathVariable BigDecimal massimo,
			Pageable page) {
		Page<Fattura> list = service.findByRangeImporto(minimo, massimo, page);
		if (list.hasContent()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>("Non cè nessuna fattura fatta in questo range di importi!", HttpStatus.BAD_REQUEST);

	}
	

	@GetMapping("/ricercapernumero/{numero}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> findByNumero(@PathVariable Long numero) {
		Optional<Fattura> list = service.findByNumero(numero);
		if (list.isPresent()) {
			return new ResponseEntity<>(list.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Non cè nessuna fattura con questo numero!", HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping("/allfatture")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Fattura>> gettAll(Pageable page) {
		
	return new ResponseEntity<>(service.findAllFatture(page), HttpStatus.OK);
		
	}
	
	

}