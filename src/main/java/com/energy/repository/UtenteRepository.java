package com.energy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energy.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	List<Utente> findByNome(String nome);

	Optional<Utente> findByUserName(String username);
	
	boolean existsByUserName(String username);

	boolean existsByEmail(String email);
	

}
