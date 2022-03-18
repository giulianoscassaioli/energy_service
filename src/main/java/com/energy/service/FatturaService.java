package com.energy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.energy.exception.CrmException;
import com.energy.model.Cliente;
import com.energy.model.Fattura;
import com.energy.model.Indirizzo;
import com.energy.repository.FatturaRepository;

@Service
public class FatturaService {

	@Autowired
	FatturaRepository fatturaRepository;
	
    String regex  = "((?:19|20)\\d\\d)-(0?[1-9]|1[012])-([12][0-9]|3[01]|0?[1-9])";
	
    Pattern pattern = Pattern.compile(regex);

	public Fattura update(Long id, Fattura fattura) {
		Optional<Fattura> fe = fatturaRepository.findById(id);
		if (fe.isPresent()) {
			Fattura fatturaUpdate = fe.get();
			fatturaUpdate.setAnno(fattura.getAnno());
			fatturaUpdate.setDataFattura(fattura.getDataFattura());
			fatturaUpdate.setImporto(fattura.getImporto());
			fatturaUpdate.setNumero(fattura.getNumero());
			fatturaUpdate.setStatoFattura(fattura.getStatoFattura());
			fatturaUpdate.setCliente(fattura.getCliente());
			fatturaRepository.save(fatturaUpdate);
			return fatturaUpdate;
		} else {
			throw new CrmException("La fattura con questo id non esiste!");
		}
	}

	public Fattura save(Fattura fattura) {
		if(fatturaRepository.existsByNumero(fattura.getNumero())) {
			throw new CrmException("Esiste gia una fattura con questo numero!");
		}
		Matcher matcher = pattern.matcher(fattura.getDataFattura().toString());
		boolean bool = matcher.matches();
		if(!bool) {
			throw new CrmException("La data fattura deve essere nel formato yyyy-MM-dd");
		}
		
		return fatturaRepository.save(fattura);
	}

	public Optional<Fattura> findById(Long id) {
		return fatturaRepository.findById(id);
	}
	
	public Page<Fattura> findAllFatture(Pageable page){
		return fatturaRepository.findAll(page);
	}
	
	public Page<Fattura> findAllFatturePage(Integer pageNo,Integer size){
		Pageable page=PageRequest.of(pageNo -1,size);
		return fatturaRepository.findAll(page);
	}
	
	public Optional<Fattura> findByNumero(Long numero) {
		return fatturaRepository.findByNumero(numero);
	}

	public void deleteById(Long id) {
		Optional<Fattura> fat =fatturaRepository.findById(id);
		if(fat.isPresent()) {
			fatturaRepository.deleteByIdWithJPQL(fat.get().getId());
		}
	}

	public Page<Fattura> findByCliente(String cliente, Pageable page) {
		return fatturaRepository.findByCliente(cliente, page);

	}

	public Page<Fattura> findByStato(String nome, Pageable page) {
		return fatturaRepository.findByStatoFattura(nome, page);

	}
	

	public Page<Fattura> findByData(LocalDate data, Pageable page) {
		return fatturaRepository.findByDataFattura(data, page);

	}

	public Page<Fattura> findByAnno(Integer anno, Pageable page) {
		return fatturaRepository.findByAnno(anno, page);
	}

	public Page<Fattura> findByRangeImporto(BigDecimal minimo, BigDecimal massimo, Pageable page) {
		return fatturaRepository.findByImportoBetweenOrderByImporto(minimo, massimo, page);
	}

}
