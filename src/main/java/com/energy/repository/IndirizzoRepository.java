package com.energy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.energy.model.Indirizzo;


public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {

	
	 Page<Indirizzo> getByVia(String via,Pageable page);
	 
	List<Indirizzo> findByViaAndCivicoAndCap(String via,int civico,String cap);
	//Optional<Indirizzo> findByViaAndCivicoAndCapAndComuneNome(String via,int civico,String cap,String comuneNome);
	List<Indirizzo> findByViaAndCivicoAndCapAndComuneNome(String via,int civico,String cap,String comuneNome);
}
