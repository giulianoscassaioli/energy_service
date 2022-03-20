package com.energy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.energy.model.Provincia;


public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	
	Optional<Provincia> findByNome(String nome);
	Optional<Provincia> findByNomeLike(String nome);

}
