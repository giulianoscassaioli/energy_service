package com.energy.controller.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.energy.exception.CrmException;
import com.energy.model.Cliente;
import com.energy.model.Comune;
import com.energy.model.Fattura;
import com.energy.model.Indirizzo;
import com.energy.model.TipoCliente;
import com.energy.repository.ClienteRepository;
import com.energy.repository.ComuneRepository;
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
	ComuneRepository comuneRepository;

	@Autowired
	IndirizzoRepository ind;

	@Autowired
	IndirizzoService indservice;
	

	@GetMapping("/clienti")
	public ModelAndView viewCorsi(Pageable page,@RequestParam(required = false, defaultValue = "0") Integer pageNumber
			, Integer size) {
		ModelAndView model = new ModelAndView();
		Pageable pageable = PageRequest.of(pageNumber,10);
		Page<Cliente> find= service.findAllClienti(pageable);
		int totalPages = find.getTotalPages();
        long totalItems = find.getTotalElements();
        model.addObject("currentPage", pageNumber);
        model.addObject("totalPages", totalPages);
    	model.addObject("totalItems", totalItems);
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
	public ModelAndView getByfatturato(@RequestParam Double fatturato, Pageable page,
			@RequestParam(defaultValue = "0") Integer pageNumber,Integer size) {
		ModelAndView model = new ModelAndView();
		Page<Cliente> list = service.findByFatturatoAnnuale(page.withPage(pageNumber), fatturato);
		int totalPages = list.getTotalPages();
	    long totalItems = list.getTotalElements();
	    model.addObject("currentPage", pageNumber);
	    model.addObject("totalPages", totalPages);
	    model.addObject("totalItems", totalItems);
		model.addObject("clienti", list);
		model.setViewName("clientigest");
		return model;
	}


	@GetMapping("/getbypartedelnome")
	public ModelAndView getClientiByParteDelNome(Pageable page,
			@RequestParam(defaultValue = "0") Integer pageNumber,Integer size,String nome) {
		ModelAndView model = new ModelAndView();
		Page<Cliente> list = service.findByParteDelNome(nome, page.withPage(pageNumber));
		int totalPages = list.getTotalPages();
	    long totalItems = list.getTotalElements();
	    model.addObject("currentPage", pageNumber);
	    model.addObject("totalPages", totalPages);
	    model.addObject("totalItems", totalItems);
		model.addObject("clienti", list);
		model.setViewName("clientigest");
		return model;

	}

	@GetMapping("/aggiungicliente")
	public String salvaCliente(Model model) {
     List<Comune> comuni= comuneRepository.findByOrderByNome();
     model.addAttribute("comuni", comuni);
		return "salvacliente";
	}

	@GetMapping("/salvacliente")
	public String saveCliente(@RequestParam(required = false) String comuneSedeOperativa,String comuneSedeLegale, String capSedeOperativa,
			String civicoSedeOperativa, String viaSedeOperativa, String localitaSedeLegale, String capSedeLegale,
			String civicoSedeLegale, String viaSedeLegale, String ragioneSociale, String partitaIva,
			TipoCliente tipoCliente, String email, String pec, String telefono, String nomeContatto,
			String cognomeContatto, String telefonoContatto, String emailContatto, String indirizzoSedeOperativa,
			String indirizzoSedeLegale,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataUltimoContatto, Double fatturatoAnnuale,
			Pageable page,Model model) {
	
		Comune comuneSOp=comuneRepository.findByNome(comuneSedeOperativa).get(0);
		Indirizzo sedeLegale = new Indirizzo();
		sedeLegale.setCap(capSedeLegale);
		sedeLegale.setCivico(Integer.valueOf(civicoSedeLegale));
		sedeLegale.setLocalita(localitaSedeLegale);
		sedeLegale.setVia(viaSedeLegale);
		sedeLegale.setComune(comuneSOp);
		
		Comune comuneSLegale=comuneRepository.findByNome(comuneSedeLegale).get(0);
		Indirizzo sedeOperativa = new Indirizzo();
		sedeOperativa.setCap(capSedeOperativa);
		sedeOperativa.setCivico(Integer.valueOf(civicoSedeOperativa));
		sedeOperativa.setComune(comuneSLegale);
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
		c.setDataInserimento(LocalDate.now());
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
		List<Comune> comuni= comuneRepository.findByOrderByNome();
	    model.addObject("comuni", comuni);
		model.addObject("id", id);
		model.addObject("cliente", cliente);
		
		return model;
	}
	
	@PostMapping("/aggiornaCliente2/{id}")
	public String aggiorna2(@ModelAttribute("cliente")Cliente cliente,Model model, @PathVariable Long id){
		
		String viaLegale = cliente.getIndirizzoSedeLegale().getVia();
		int civicoLegale = cliente.getIndirizzoSedeLegale().getCivico();
		String capLegale = cliente.getIndirizzoSedeLegale().getCap();
		String comuneLeg =cliente.getIndirizzoSedeLegale().getComune().getNome();
	    
		
		
		List<Indirizzo> indirizzoLegale=ind.findByViaAndCivicoAndCapAndComuneNome(viaLegale, civicoLegale, capLegale, comuneLeg);

		if(!indirizzoLegale.isEmpty()) {	
			cliente.setIndirizzoSedeLegale(indirizzoLegale.get(0));
		} else {
			Indirizzo indirizzoLeg = new Indirizzo();
			indirizzoLeg.setCap(capLegale);
			indirizzoLeg.setCivico(civicoLegale);
			indirizzoLeg.setVia(viaLegale);
			System.out.println(cliente.getIndirizzoSedeLegale().getComune().getId());
			Comune comuneLegale = comuneRepository.findById(cliente.getIndirizzoSedeLegale().getComune().getId()).get();
			indirizzoLeg.setComune(comuneLegale);
			ind.save(indirizzoLeg);
			cliente.setIndirizzoSedeLegale(indirizzoLeg);
		}
	
		String viaOperativa = cliente.getIndirizzoSedeOperativa().getVia();
		int civicoOperativa = cliente.getIndirizzoSedeOperativa().getCivico();
		String capOperativa = cliente.getIndirizzoSedeOperativa().getCap();
		String comuneOp =cliente.getIndirizzoSedeOperativa().getComune().getNome();
		String localitaOperativa = cliente.getIndirizzoSedeOperativa().getLocalita();
		

		List<Indirizzo> indirizzoOperativa=ind.findByViaAndCivicoAndCapAndComuneNome(viaLegale, civicoLegale, capLegale, comuneOp);
		if(!indirizzoOperativa.isEmpty()) {
			cliente.setIndirizzoSedeOperativa(indirizzoOperativa.get(0));
		} else {
			Indirizzo indirizzoOpe = new Indirizzo();
			indirizzoOpe.setCap(capOperativa);
			indirizzoOpe.setCivico(civicoOperativa);
			indirizzoOpe.setLocalita(localitaOperativa);
			indirizzoOpe.setVia(viaOperativa);
			System.out.println(cliente.getIndirizzoSedeOperativa().getComune().getId());
			Comune comuneOperativa = comuneRepository.findById(cliente.getIndirizzoSedeOperativa().getComune().getId()).get();
	
			indirizzoOpe.setComune(comuneOperativa);
			ind.save(indirizzoOpe);
			cliente.setIndirizzoSedeOperativa(indirizzoOpe);
		}
	
		cliente.setId(id);

		Cliente c= service.findById(id).get();

	    cliente.setDataInserimento(c.getDataInserimento());
		
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
