package com.energy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.energy.model.Indirizzo;


public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {

	
	 Page<Indirizzo> getByVia(String via,Pageable page);
}
