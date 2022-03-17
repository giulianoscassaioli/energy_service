package com.energy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.energy.model.Provincia;
import com.energy.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	ProvinciaRepository provincia;

	public Optional<Provincia> getByNome(String string) {
		return provincia.findByNome(string);
	}
	
	public Optional<Provincia> getById(Long id) {
		return provincia.findById(id);
	}
	
	public Page<Provincia> getAll(Pageable page) {
		return provincia.findAll(page);
	}

}
