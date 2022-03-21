package com.energy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.energy.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Page<Cliente> findByRagioneSocialeLikeIgnoreCase(String ragioneSociale,Pageable pageable);
	
	Optional<Cliente> findByRagioneSociale(String ragioneSociale);
	
	boolean existsByEmail(String email);
	
	boolean existsByTelefono(String telefono);
	
	boolean existsByPartitaIva(String partitaIva);
	
	boolean existsByPec(String pec);
	
	boolean existsById(Long id);
	
	Page<Cliente> findByOrderByRagioneSociale(Pageable pageable);
	
	Page<Cliente> findByOrderByFatturatoTotale(Pageable page);
	
	Page<Cliente> findByOrderByDataInserimento(Pageable page);
   
	Page<Cliente> findByOrderByDataUltimoContatto(Pageable page);
	
	Page<Cliente> findByOrderByIndirizzoSedeLegaleComuneProvinciaNome(Pageable page);
	
	Page<Cliente> findByFatturatoTotale (Pageable page, BigDecimal fatturato);
	
	Page<Cliente> findByDataInserimento (Pageable page, LocalDate data);
	
	Page<Cliente> findByDataUltimoContatto (Pageable page, LocalDate data);

	Optional<Cliente> getByNomeContatto(String nomeContatto);
	
	Optional<Cliente> findByIndirizzoSedeOperativaId(Long id);
	
	Optional<Cliente> findByIndirizzoSedeLegaleId(Long id);
	
	Optional<Cliente> findByEmail(String email);
	
	Optional<Cliente> findByTelefono(String telefono);
	
	Optional<Cliente> findByPartitaIva(String partitaIva);
	
	Optional<Cliente> findByPec(String pec);


}
