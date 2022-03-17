package com.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.energy.exception.CrmException;
import com.energy.model.Cliente;
import com.energy.model.Indirizzo;
import com.energy.repository.ClienteRepository;
import com.energy.repository.ComuneRepository;
import com.energy.repository.IndirizzoRepository;

@Service
public class IndirizzoService {

	@Autowired
	IndirizzoRepository indirizzo;
	
	@Autowired
	ComuneRepository comune;
	
	@Autowired
	ClienteRepository cliente;

	public void save(Indirizzo c) {
		indirizzo.save(c);
	}

	public void deleteById(Long id) {
		Optional<Indirizzo> ind =indirizzo.findById(id);
		if(ind.isPresent()) {
			Optional<Cliente> cli =cliente.findById(ind.get().getId());
			Optional<Cliente> cliop= cliente.findByIndirizzoSedeOperativaId(ind.get().getId());
			Optional<Cliente> clisedleg= cliente.findByIndirizzoSedeLegaleId(ind.get().getId());
			ind.get().setComune(null);
			if(cliop.isPresent()) {
				cliop.get().setIndirizzoSedeOperativa(null);
			}
			if(clisedleg.isPresent()) {
				clisedleg.get().setIndirizzoSedeLegale(null);
			}
			indirizzo.deleteById(ind.get().getId());
		}
		
	}

	public Page<Indirizzo> getAllIndirizzi(Pageable page) {
		return indirizzo.findAll(page);
	}

	public Optional<Indirizzo> getIndirizzoById(Long id) {
		return indirizzo.findById(id);
	}

	public Page<Indirizzo> getIndirizzoByVia(String via, Pageable page) {
		return indirizzo.getByVia(via, page);
	}

	public void update(Long id, Indirizzo ind) {
		Optional<Indirizzo> ce = indirizzo.findById(id);
		if (ce.isPresent()) {
			Indirizzo indirizzoUpdate = ce.get();
			indirizzoUpdate.setCap(ind.getCap());
			indirizzoUpdate.setCivico(ind.getCivico());
			indirizzoUpdate.setVia(ind.getVia());
			indirizzoUpdate.setLocalita(ind.getLocalita());
			indirizzo.save(indirizzoUpdate);
		} else {
			throw new CrmException("L'indirizzo con questo id non esiste!");
		}
	}
}
