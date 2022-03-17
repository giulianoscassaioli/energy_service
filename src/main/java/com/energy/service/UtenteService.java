package com.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energy.exception.CrmException;
import com.energy.model.Utente;
import com.energy.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	UtenteRepository utenteRepository;

	public Optional<Utente> findById(Long idUtente) {
		return utenteRepository.findById(idUtente);
	}

	public List<Utente> findByNome(String nome) {
		return utenteRepository.findByNome(nome);
	}

	public Optional<Utente> findByUsername(String username) {
		return utenteRepository.findByUserName(username);
	}

	public List<Utente> findAll() {
		return utenteRepository.findAll();
	}

	public Utente save(Utente utente) {
		return utenteRepository.save(utente);
	}

	public Utente update(Long id, Utente utente) {
		Optional<Utente> utenteResult = utenteRepository.findById(id);

		if (utenteResult.isPresent()) {
			Utente utenteUpdate = utenteResult.get();
			utenteUpdate.setNome(utente.getNome());
			utenteUpdate.setUserName(utente.getUserName());
			utenteUpdate.setEmail(utente.getEmail());
			utenteUpdate.setPassword(utente.getPassword());
			utenteUpdate.setActive(utente.isActive());
			utenteRepository.save(utenteUpdate);
			return utenteUpdate;
		} else {
			throw new CrmException("L'Utente  con questo id non esiste!");
		}

	}

	public void delete(Long id) {
		utenteRepository.deleteById(id);
	}

}
