package com.energy.controller.web;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.energy.model.Cliente;
import com.energy.model.Fattura;
import com.energy.service.ClienteService;
import com.energy.service.FatturaService;

@Controller
@RequestMapping("/web/fatture")
public class FatturaControllerWeb {

	@Autowired
	FatturaService service;
	
	@Autowired
	ClienteService clienteservice;

	@GetMapping("/aggiungifattura")
	public ModelAndView salvaFattura(Pageable page) {
        Page<Cliente> clienti=clienteservice.findAllClienti(page);
		ModelAndView model= new ModelAndView();
		model.setViewName("salvafattura");
		model.addObject("clienti",clienti.getContent());
		return model;
	}

	@PostMapping("/salvafattura")
	public ModelAndView save(@RequestParam String ragioneSociale,
			Long numero, Integer anno, BigDecimal importo, String stato,Pageable page) {
		ModelAndView mv = new ModelAndView();
		service.save(new Fattura(clienteservice.findByParteDelNome(ragioneSociale, page).getContent().get(0), LocalDate.now(), numero, anno, importo, stato));
		mv.addObject("fatture", service.findAllFatture(page));
		mv.setViewName("fatturegest");
		return mv;
	}
	
	
	@GetMapping("/aggiornaFattura/{id}")
	public ModelAndView aggiorna(@PathVariable Long id){
	
		Fattura fattura= service.findById(id).get();
		ModelAndView model=new ModelAndView("aggiornafattura");
		model.addObject("id", id);
		model.addObject("fattura", fattura);
		
		return model;
	}
	
	@PostMapping("/aggiornaFattura2/{id}")
	public String aggiorna2(Fattura fattura,Model model, @PathVariable Long id){
		Fattura f= service.findById(id).get();
		fattura.setCliente(f.getCliente());
		fattura.setDataFattura(f.getDataFattura());
		fattura.setNumero(f.getNumero());
		  service.update(id, fattura);
	   return "redirect:/web/fatture/fatture";
		
	}
	//
	@GetMapping("/getbyrangeimporto")
	public ModelAndView getfatturabyrangeimportoviewadmin(Pageable page,@RequestParam(defaultValue = "0") BigDecimal minimo,
			@RequestParam(defaultValue = "100000") BigDecimal massimo
			, Integer size,@RequestParam(defaultValue = "0") Integer pageNumber) {
		ModelAndView myModel=new ModelAndView();
		Pageable pageable = PageRequest.of(pageNumber,10);
		Page<Fattura> list = service.findByRangeImporto(minimo, massimo, pageable);
		int totalPages = list.getTotalPages();
	    long totalItems = list.getTotalElements();
	    List<Fattura> fatture = list.getContent();
	    myModel.addObject("currentPage", pageNumber);
	    myModel.addObject("totalPages", totalPages);
	    myModel.addObject("totalItems", totalItems);

		myModel.addObject("fatture", list);
		myModel.setViewName("fatturegest");
		return myModel;

		}
	
	@GetMapping("/getbydata")
	public ModelAndView getfatturabydata(Pageable page, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
			@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "50") Integer size) {

		ModelAndView myModel=new ModelAndView();
		Pageable pag = PageRequest.of(pageNumber, size);
		Page<Fattura> list = service.findByData(data, pag);
		int totalPages = list.getTotalPages();
	    long totalItems = list.getTotalElements();
	    myModel.addObject("currentPage", pageNumber);
	    myModel.addObject("totalPages", totalPages);
	    myModel.addObject("totalItems", totalItems);
		myModel.addObject("fatture", list);
		myModel.addObject("totalPages",list.getTotalPages());
		myModel.setViewName("fatturegest");
		return myModel;
	}
	
	
	@GetMapping("/getbycliente")
	public ModelAndView getfatturabyclienteviewadmin(@RequestParam(required = false)String cliente,
			@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "50") Integer size) {
		ModelAndView myModel = new ModelAndView();
		Pageable pag = PageRequest.of(pageNumber, 10);
		Page<Fattura> list = service.findByCliente(cliente, pag);
		int totalPages = list.getTotalPages();
		int currentPage= list.getNumber();
//		if(totalPages==0) {
//			totalPages=1;
//		}
	    long totalItems = list.getTotalElements();
	    myModel.addObject("currentPage", currentPage);
	    myModel.addObject("totalPages", totalPages);
	    myModel.addObject("totalItems", totalItems);
		myModel.addObject("fatture", list);
		myModel.setViewName("fatturegest");
		return myModel;
	}
	
	@GetMapping("/fatture")
	public ModelAndView viewFatture(Pageable page,@RequestParam(required = false, defaultValue = "0") Integer pageNumber
			, Integer size) {
		ModelAndView model = new ModelAndView();
		Pageable pageable = PageRequest.of(pageNumber,10);
		Page<Fattura> find= service.findAllFatture(pageable);
		int totalPages = find.getTotalPages();
	    long totalItems = find.getTotalElements();
	    model.addObject("currentPage", pageNumber);
	    model.addObject("totalPages", totalPages);
	    model.addObject("totalItems", totalItems);
		model.addObject("fatture", find);
		model.addObject("totalPages",find.getTotalPages());
		model.setViewName("fatturegest");
		return model;

	}
	
	
	
	@GetMapping("/eliminaFattura/{id}")
	public String elimina(Model model,@PathVariable Long id){
		service.deleteById(id);
		return "redirect:/web/fatture/fatture";
		
	}


}