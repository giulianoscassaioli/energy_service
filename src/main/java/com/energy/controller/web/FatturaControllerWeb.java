package com.energy.controller.web;

import java.math.BigDecimal;
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
	public ModelAndView save(@RequestParam String ragioneSociale, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
			Long numero, Integer anno, BigDecimal importo, String stato,Pageable page) {
		ModelAndView mv = new ModelAndView();
		service.save(new Fattura(clienteservice.findByParteDelNome(ragioneSociale, page).getContent().get(0), data, numero, anno, importo, stato));
		mv.addObject("fatture", service.findAllFatture(page));
		mv.setViewName("fatturegest");
		return mv;
	}
	
//	@GetMapping("/getbyrangeimporto")
//	public ModelAndView getfatturabyrangeimportoviewadmin(@RequestParam(defaultValue = "0") BigDecimal minimo,
//			@RequestParam(defaultValue = "100000") BigDecimal massimo, Pageable page) {
//		ModelAndView myModel=new ModelAndView();
//		Page<Fattura> list = service.findByRangeImporto(minimo, massimo, page);
//		myModel.addObject("fatture", list);
//		myModel.setViewName("fatturegest");
//		return myModel;
//
//		}
	
	@GetMapping("/getbyrangeimporto")
	public ModelAndView getfatturabyrangeimportoviewadmin(@RequestParam(defaultValue = "0") BigDecimal minimo,
			@RequestParam(defaultValue = "100000") BigDecimal massimo
			, Integer size, @RequestParam(required = false, defaultValue = "1")Integer pageNo
			,Pageable page) {
		ModelAndView myModel=new ModelAndView();
		Page<Fattura> list = service.findByRangeImporto(minimo, massimo, page.withPage(pageNo-1));
		myModel.addObject("fatture", list);
		myModel.setViewName("fatturegest");
		return myModel;

		}
	
	@GetMapping("/getbydata")
	public ModelAndView getfatturabydata(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "50") Integer size) {
		ModelAndView myModel=new ModelAndView();
		Pageable pag = PageRequest.of(page, size);
		Page<Fattura> list = service.findByData(data, pag);
		myModel.addObject("fatture", list);
		myModel.setViewName("fatturegest");
		return myModel;
	}
	
	@GetMapping("/getbycliente")
	public ModelAndView getfatturabyclienteviewadmin(@RequestParam String cliente,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "50") Integer size) {
		ModelAndView myModel = new ModelAndView();
		Pageable pag = PageRequest.of(page, size);
		Page<Fattura> list = service.findByCliente(cliente, pag);
		myModel.addObject("fatture", list);
		myModel.setViewName("fatturegest");
		return myModel;
	}
	
	@GetMapping("/fatture")
	public ModelAndView viewFatture(Pageable page,@RequestParam(required = false, defaultValue = "1") Integer pageNumber
			, Integer size) {
		ModelAndView model = new ModelAndView();
		Page<Fattura> find= service.findAllFatture(page.withPage(pageNumber -1));
		model.addObject("currentPage", pageNumber);
		model.addObject("fatture", find);
		model.setViewName("fatturegest");
		return model;

	}
	
	
	@GetMapping("/eliminaFattura/{id}")
	public String elimina(Model model,@PathVariable Long id){
		service.deleteById(id);
		return "redirect:/web/fatture/fatture";
		
	}


}