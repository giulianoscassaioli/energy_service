package com.energy.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.energy.repository.ClienteRepository;
import com.energy.service.ClienteService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	ClienteService service;
	
	@Autowired
	ClienteRepository repository;

	@PostMapping("/salvacliente")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> save(@RequestBody Cliente c) {
		Optional<Cliente> cc= service.findById(c.getId());
		if(!cc.isPresent()){
			Cliente save =  service.save(c);
		return  new ResponseEntity<>(save, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Il Cliente con questo id esiste gia", HttpStatus.BAD_REQUEST);
		}
	}
	

	@DeleteMapping("/eliminacliente/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> eliminaCliente(@PathVariable(required = true) Long id) {
		Optional<Cliente> cc= service.findById(id);
		if(cc.isPresent()){
		   service.delete(id);
		   repository.flush();
		return new ResponseEntity<>("Cliente cancellato!", HttpStatus.OK);
		}
		return new ResponseEntity<>("Il Cliente con questo id non esiste!", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/trovacliente/id/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> findByIdCliente(@PathVariable(required = true) Long id) {
		Optional<Cliente> cc= service.findById(id);
		if(cc.isPresent()){
		return new ResponseEntity<>(cc.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Il Cliente con questo id non esiste!", HttpStatus.NOT_FOUND);
	}
	

	@PutMapping("/aggiornacliente/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> aggiornaCliente(@PathVariable(required = true) Long id, @RequestBody Cliente c) {
		Cliente save = service.update(id, c);
		repository.flush();
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@GetMapping("/ordinaperragionesociale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> findAllByRagioneSociale(Pageable page) {
		
		Page<Cliente> list = service.findAllOrderByRagioneSociale(page);
		if(list.hasContent()) {
		return new ResponseEntity<>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>("Non cè nessun cliente con questa ragione sociale!", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/ordinaperfatturato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Cliente>> ordinaByFatturato(Pageable page) {
		Page<Cliente> list = service.findByFatturatoAnnuale(page);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/ordinaperdatains")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Cliente>> ordinaByDataInserimento(Pageable page) {
		
		Page<Cliente>list = service.findByListaDataInserimento(page);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/ordinaperdataultimocontatto")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findByDataUltimoContatto(Pageable page) {
		
		Page<Cliente>list = service.findByListaDataUltimoContatto(page);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/ordinaperprovinciasedeleg")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findByIndirizzoSedeLegaleComuneProvincia(Pageable page) {
		
		Page<Cliente>list = service.findByOrderByIndirizzoSedeLegaleComuneProvincia(page);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//
	@GetMapping("/trovaperpartedelnome/{nome}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> findByParteDelNome (@PathVariable String nome,Pageable pageable){
		 Page<Cliente> clienti=service.findByParteDelNome("%"+nome+"%", pageable);
		 if(clienti.hasContent()) {
			 return new ResponseEntity<>(clienti.getContent(), HttpStatus.OK);
		 }
		
		return new ResponseEntity<>("Non cè nessun cliente con questo nome!", HttpStatus.BAD_REQUEST);
	 }
	 
	@GetMapping("/trovaperfatturatoannuale/{fatturato}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	 public ResponseEntity<?> cercaByFatturatoAnnuale (Pageable page,@PathVariable BigDecimal fatturato){
		Page<Cliente> clienti=service.findByFatturatoAnnuale(page,fatturato);
		 if(clienti.hasContent()) {
			 return new ResponseEntity<>(clienti.getContent(), HttpStatus.OK);
		 }
		
		return new ResponseEntity<>("Non cè nessun cliente con questo fatturato annuo!", HttpStatus.BAD_REQUEST);
	 }
		
	
	 
	@GetMapping("/trovaperdatainserimento/{data}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	 public ResponseEntity<?> findByDataInserimento (Pageable page,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data){
		Page<Cliente> clienti=service.findByDataInserimento(page,data);
		 if(clienti.hasContent()) {
			 return new ResponseEntity<>(clienti.getContent(), HttpStatus.OK);
		 }
		
		return new ResponseEntity<>("Non cè nessun cliente inserito in questa data!", HttpStatus.BAD_REQUEST);
		
	 }
	 
	@GetMapping("/trovaperdataultimocontatto/{data}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	 public ResponseEntity<?> findByDataUltimoContatto (Pageable page,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data){
		Page<Cliente> clienti=service.findByDataUltimoContatto(page,data);
		 if(clienti.hasContent()) {
			 return new ResponseEntity<>(clienti.getContent(), HttpStatus.OK);
		 }
		
		return new ResponseEntity<>("Non cè nessun cliente con questa data ultimo contatto!", HttpStatus.BAD_REQUEST);
		
	 }
	
	@GetMapping("/trovatutticlienti")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
     public ResponseEntity<?> findAllClienti (Pageable page){
		 return new ResponseEntity<>(service.findAllClienti(page) , HttpStatus.OK);
		 
	 }
	 
}
