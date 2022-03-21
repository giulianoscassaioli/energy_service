package com.energy.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.energy.exception.CrmException;
import com.energy.model.Cliente;
import com.energy.model.TipoCliente;
import com.energy.repository.ClienteRepository;

import java.util.regex.Matcher;

@Service
public class ClienteService {
	
	String regex = "^(.+)@(\\S+)$";
	
	String regex2 = "((?:19|20)\\d\\d)-(0?[1-9]|1[012])-([12][0-9]|3[01]|0?[1-9])";
	
    Pattern pattern = Pattern.compile(regex2);
     
     
	
	@Autowired
	ClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {
		Optional<Cliente> ragionesoc= clienteRepository.findByRagioneSociale(cliente.getRagioneSociale());
		
		if(ragionesoc.isPresent()) {
			throw new CrmException("Esiste gia un cliente con questa ragione sociale!");
		}
		
		if(clienteRepository.existsByEmail(cliente.getEmail())) {
			throw new CrmException("Esiste gia un cliente con questa e-mail!");
		}
		
		if(!cliente.getEmail().matches(regex)) {
			throw new CrmException("Devi inserire una e-mail valida!");
		}
		
		if(clienteRepository.existsByTelefono(cliente.getTelefono())) {
			throw new CrmException("Esiste gia un cliente con questo numero di telefono!");
		}
		
		if(!controlloCampo(cliente.getTelefono())) {
			throw new CrmException("Devi inserire un numero di telefono valido!");
		}
		
		
		if(clienteRepository.existsByPartitaIva(cliente.getPartitaIva())) {
			throw new CrmException("Esiste gia un cliente con questa partita iva!");
		}
		
		if(clienteRepository.existsByPec(cliente.getPec())) {
			throw new CrmException("Esiste gia un cliente con questa pec!");
		}
		
		if(!cliente.getPec().matches(regex)) {
			throw new CrmException("Devi inserire una pec valida!");
		}
		
		
		Matcher matcher = pattern.matcher(cliente.getDataInserimento().toString());
		Matcher matcher1 = pattern.matcher(cliente.getDataUltimoContatto().toString());
		boolean bool = matcher.matches();
		boolean bool1 = matcher1.matches();
		if(!bool) {
			throw new CrmException("La data inserimento deve essere nel formato yyyy-MM-dd");
		}
		
		if(!bool1) {
			throw new CrmException("La data ultimo contatto deve essere nel formato yyyy-MM-dd");
		}
		 
		return clienteRepository.save(cliente);
	}

	public void delete(Long id) {
		clienteRepository.deleteById(id);

	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);

	}

	public Cliente update(Long id, Cliente c) {
		
		if(clienteRepository.existsByEmail(c.getEmail())) {
			Cliente cliEmail=clienteRepository.findByEmail(c.getEmail()).get();
			if(id != cliEmail.getId()) {
			  throw new CrmException("Esiste gia un cliente con questa e-mail!");
			}
		}
		
		if(!c.getEmail().matches(regex)) {
			throw new CrmException("Devi inserire una e-mail valida!");
		}
		
		if(clienteRepository.existsByTelefono(c.getTelefono())) {
			Cliente cliEmail=clienteRepository.findByTelefono(c.getTelefono()).get();
			if(id != cliEmail.getId()) {
			throw new CrmException("Esiste gia un cliente con questo numero di telefono!");
			}
		}
		
		if(!controlloCampo(c.getTelefono())) {
			throw new CrmException("Devi inserire un numero di telefono valido!");
		}
		
		
		if(clienteRepository.existsByPartitaIva(c.getPartitaIva())) {
			Cliente cliEmail=clienteRepository.findByPartitaIva(c.getPartitaIva()).get();
			if(id != cliEmail.getId()) {
			throw new CrmException("Esiste gia un cliente con questa partita iva!");
			}
			
		}
		
		if(clienteRepository.existsByPec(c.getPec())) {
			Cliente cliEmail=clienteRepository.findByPec(c.getPec()).get();
			if(id != cliEmail.getId()) {
			throw new CrmException("Esiste gia un cliente con questa pec!");
			}
		}
		
		if(!c.getPec().matches(regex)) {
			throw new CrmException("Devi inserire una pec valida!");
		}
		
		Matcher matcher = pattern.matcher(c.getDataInserimento().toString());
		Matcher matcher1 = pattern.matcher(c.getDataUltimoContatto().toString());
		boolean bool = matcher.matches();
		boolean bool1 = matcher1.matches();
		if(!bool) {
			throw new CrmException("La data inserimento deve essere nel formato yyyy-MM-dd");
		}
		
		if(!bool1) {
			throw new CrmException("La data ultimo contatto deve essere nel formato yyyy-MM-dd");
		}
		
		Optional<Cliente> cli = clienteRepository.findById(id);
		
		if (cli.isPresent()) {
	
			Cliente cliUpdate = cli.get();
			cliUpdate.setCognomeContatto(c.getCognomeContatto());
			cliUpdate.setDataInserimento(c.getDataInserimento());
			cliUpdate.setDataUltimoContatto(c.getDataUltimoContatto());
			cliUpdate.setEmail(c.getEmail());
			cliUpdate.setEmailContatto(c.getEmailContatto());
			cliUpdate.setFatturatoAnnuale(c.getFatturatoAnnuale());
			cliUpdate.setIndirizzoSedeLegale(c.getIndirizzoSedeLegale());
			cliUpdate.setIndirizzoSedeOperativa(c.getIndirizzoSedeOperativa());
			cliUpdate.setNomeContatto(c.getNomeContatto());
			cliUpdate.setPartitaIva(c.getPartitaIva());
			cliUpdate.setPec(c.getPec());
			cliUpdate.setRagioneSociale(c.getRagioneSociale());
			cliUpdate.setTelefono(c.getTelefono());
			cliUpdate.setTelefonoContatto(c.getTelefonoContatto());
			cliUpdate.setTipoCliente(c.getTipoCliente());
			clienteRepository.save(cliUpdate);
			clienteRepository.flush();
			return cliUpdate;
		} else {
			throw new CrmException("Il Cliente con questo id non esiste!");
		}
	}
	
	

	public Page<Cliente> findAllOrderByRagioneSociale(Pageable pageable) {
		return clienteRepository.findByOrderByRagioneSociale(pageable);
	}

	public Page<Cliente> findByParteDelNome(String nome, Pageable pageable) {
		return clienteRepository.findByRagioneSocialeLikeIgnoreCase("%" + nome + "%", pageable);
	}
	
	public Page<Cliente> findAllClienti(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}


	public Page<Cliente> findByFatturatoAnnuale(Pageable page, Double fatturato) {
		return clienteRepository.findByFatturatoAnnuale(page, fatturato);
	}

	public Page<Cliente> findByDataInserimento(Pageable page, LocalDate data) {
		return clienteRepository.findByDataInserimento(page, data);
	}

	public Page<Cliente> findByDataUltimoContatto(Pageable page, LocalDate data) {
		return clienteRepository.findByDataUltimoContatto(page, data);
	}

	public Page<Cliente> findByFatturatoAnnuale(Pageable pageable) {
		return clienteRepository.findByOrderByFatturatoAnnuale(pageable);

	}

	public Page<Cliente> findByListaDataInserimento(Pageable pageable) {
		return clienteRepository.findByOrderByDataInserimento(pageable);

	}

	public Page<Cliente> findByListaDataUltimoContatto(Pageable pageable) {
		return clienteRepository.findByOrderByDataUltimoContatto(pageable);

	}

	public Page<Cliente> findByOrderByIndirizzoSedeLegaleComuneProvincia(Pageable page) {
		return clienteRepository.findByOrderByIndirizzoSedeLegaleComuneProvinciaNome(page);
	}
	
	public boolean controlloCampo(String campo) {
		int counter = 0;
		for (char c : campo.toCharArray()) {
			if (counter == 0 && c == '+') {
				continue;
			}

			if (counter != 0 && (c == '/' || c == '-')) {
				continue;
			}

			if (!Character.isDigit(c)) {
				return false;

			}
			counter++;
		}

		return true;
	}

}
