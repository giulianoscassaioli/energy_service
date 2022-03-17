package com.energy.controller.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.energy.exception.CrmException;
import com.energy.model.Cliente;
import com.energy.model.Fattura;
import com.energy.model.Indirizzo;
import com.energy.model.TipoCliente;
import com.energy.repository.ClienteRepository;
import com.energy.repository.IndirizzoRepository;
import com.energy.service.ClienteService;
import com.energy.service.IndirizzoService;

@Controller
@RequestMapping("/web/clienti")
public class ClienteControllerWeb {

	@Autowired
	ClienteService service;

	@Autowired
	ClienteRepository repository;

	@Autowired
	IndirizzoRepository ind;

	@Autowired
	IndirizzoService indservice;

	@GetMapping("/clienti")
	public ModelAndView viewCorsi(Pageable page,@RequestParam(required = false, defaultValue = "1") Integer pageNumber
			, Integer size) {
		ModelAndView model = new ModelAndView();
		Page<Cliente> find= service.findAllClienti(page.withPage(pageNumber -1));
		model.addObject("currentPage", pageNumber);
		model.addObject("clienti", find);
		model.setViewName("clientigest");
		return model;

	}
	
	@GetMapping("/homeclienti")
	public String home() {

		return "index";
	}

	@GetMapping("/cercaclienti")
	public String ricercaClienti() {

		return "ricercaclienti";
	}

	@GetMapping("/cercafatture")
	public String ricercaFatture() {

		return "ricercafatture";
	}

	@GetMapping("/getbyfatturato")
	public ModelAndView getByfatturato(@RequestParam Double fatturato, Pageable page) {
		ModelAndView model = new ModelAndView();
		Page<Cliente> list = service.findByFatturatoAnnuale(page, fatturato);
		model.addObject("clienti", list);
		model.setViewName("clientigest");
		return model;
	}

	@GetMapping("/getbypartedelnome")
	public ModelAndView getClientiByParteDelNome(@RequestParam String nome, Pageable page) {
		ModelAndView model = new ModelAndView();
		Page<Cliente> list = service.findByParteDelNome(nome, page);
		model.addObject("clienti", list);
		model.setViewName("clientigest");
		return model;

	}

	@GetMapping("/aggiungicliente")
	public String salvaCliente() {

		return "salvacliente";
	}

	@GetMapping("/salvacliente")
	public String saveCliente(@RequestParam String localitaSedeOperativa, String capSedeOperativa,
			String civicoSedeOperativa, String viaSedeOperativa, String localitaSedeLegale, String capSedeLegale,
			String civicoSedeLegale, String viaSedeLegale, String ragioneSociale, String partitaIva,
			TipoCliente tipoCliente, String email, String pec, String telefono, String nomeContatto,
			String cognomeContatto, String telefonoContatto, String emailContatto, String indirizzoSedeOperativa,
			String indirizzoSedeLegale, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInserimento,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto, Double fatturatoAnnuale,
			Pageable page,Model model) {
		
		Indirizzo sedeLegale = new Indirizzo();
		sedeLegale.setCap(capSedeLegale);
		sedeLegale.setCivico(Integer.valueOf(civicoSedeLegale));
		sedeLegale.setLocalita(localitaSedeLegale);
		sedeLegale.setVia(viaSedeLegale);
		Indirizzo sedeOperativa = new Indirizzo();
		sedeOperativa.setCap(capSedeOperativa);
		sedeOperativa.setCivico(Integer.valueOf(civicoSedeOperativa));
		sedeOperativa.setLocalita(localitaSedeOperativa);
		sedeOperativa.setVia(viaSedeOperativa);
		indservice.save(sedeOperativa);
		indservice.save(sedeLegale);
		Cliente c = new Cliente();
		c.setRagioneSociale(ragioneSociale);
		c.setPartitaIva(partitaIva);
		c.setTipoCliente(tipoCliente);
		c.setEmail(email);
		c.setPec(pec);
		c.setTelefono(telefono);
		c.setNomeContatto(nomeContatto);
		c.setCognomeContatto(cognomeContatto);
		c.setTelefonoContatto(telefonoContatto);
		c.setEmailContatto(emailContatto);
		c.setIndirizzoSedeOperativa(sedeOperativa);
		c.setIndirizzoSedeLegale(sedeLegale);
		c.setDataInserimento(dataInserimento);
		c.setDataUltimoContatto(dataUltimoContatto);
		c.setFatturatoAnnuale(fatturatoAnnuale);
		try {
		service.save(c);
		}catch (CrmException e){
			model.addAttribute("messaggio",e.getMessage());
			return "errore";
		}
		return "redirect:/web/clienti/clienti";
	}

	@GetMapping("/eliminaCliente/{id}")
	public String elimina(Model model, @PathVariable Long id) {
		service.delete(id);
		return "redirect:/web/clienti/clienti";

	}
	
	@GetMapping("/aggiornaCliente/{id}")
	public ModelAndView aggiorna(@PathVariable Long id){
	
		Cliente cliente= service.findById(id).get();
		ModelAndView model=new ModelAndView("aggiornacliente");
		model.addObject("id", id);
		model.addObject("cliente", cliente);
		
		return model;
	}
	
	@PostMapping("/aggiornaCliente2/{id}")
	public String aggiorna2(Cliente cliente,Model model, @PathVariable Long id, @RequestParam String localitaSedeOperativa
			,String capSedeOperativa,
			String civicoSedeOperativa, String viaSedeOperativa, String localitaSedeLegale, String capSedeLegale,
			String civicoSedeLegale, String viaSedeLegale){
		
		Indirizzo sedeLegale = new Indirizzo();
		sedeLegale.setCap(capSedeLegale);
		sedeLegale.setCivico(Integer.valueOf(civicoSedeLegale));
		sedeLegale.setLocalita(localitaSedeLegale);
		sedeLegale.setVia(viaSedeLegale);
		
		Indirizzo sedeOperativa = new Indirizzo();
		sedeOperativa.setCap(capSedeOperativa);
		sedeOperativa.setCivico(Integer.valueOf(civicoSedeOperativa));
		sedeOperativa.setLocalita(localitaSedeOperativa);
		sedeOperativa.setVia(viaSedeOperativa);
		
		indservice.save(sedeOperativa);
		indservice.save(sedeLegale);
		
		Cliente c= service.findById(id).get();
		cliente.setIndirizzoSedeLegale(sedeLegale);
		cliente.setIndirizzoSedeOperativa(sedeOperativa);
		cliente.setDataInserimento(c.getDataInserimento());
		cliente.setDataUltimoContatto(c.getDataUltimoContatto());
		try {
		service.update(id, cliente);
		}
		catch (CrmException e){
			model.addAttribute("messaggio",e.getMessage());
			return "errore";
		}
		  
		  return "redirect:/web/clienti/clienti";
		
	}

}
