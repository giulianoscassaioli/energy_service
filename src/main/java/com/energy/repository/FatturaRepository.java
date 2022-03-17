package com.energy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.energy.model.Fattura;


public interface FatturaRepository extends JpaRepository<Fattura, Long> {

	@Query("SELECT c FROM Fattura c WHERE c.cliente.ragioneSociale=:cliente")
	 Page<Fattura> findByCliente(String cliente, Pageable page);

	 Page<Fattura> findByStatoFattura(String stato,Pageable pageable);

	 Page<Fattura> findByDataFattura(LocalDate dataFattura, Pageable page);

	 Page<Fattura> findByAnno(Integer anno, Pageable page);

	 Page<Fattura> findByImportoBetweenOrderByImporto(BigDecimal minimo, BigDecimal massimo, Pageable page);
    
	 boolean existsByNumero(Long numero);
	 
	 Optional<Fattura> findByNumero(Long numero);
	 
	 Optional<Fattura> findByClienteId(Long id);
	 
	 @Modifying
	 @Transactional
	 @Query("DELETE Fattura f WHERE f.id = ?1")
	   void deleteByIdWithJPQL(Long id);

}
