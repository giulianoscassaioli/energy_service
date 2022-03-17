package com.energy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energy.model.Comune;


public interface ComuneRepository extends JpaRepository<Comune, Long> {

	
	Optional <Comune> getFirstByNome(String nome);

	
}
