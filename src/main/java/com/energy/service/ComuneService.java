package com.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.energy.model.Comune;
import com.energy.model.Provincia;
import com.energy.repository.ComuneRepository;

@Service
public class ComuneService {

	@Autowired
	ComuneRepository comune;
	
	public Optional<Comune> getByNome(String nome) {
	   return comune.getFirstByNome(nome);
	}
	
	public Optional<Comune> getById(Long id) {
		return comune.findById(id);
	}
	
	public Page<Comune> getAll(Pageable page) {
		return comune.findAll(page);
	}
	

}