package com.energy.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fattura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.EAGER)//specifica la cardinalita delle relazioni
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private Cliente cliente;
	
	
	private LocalDate dataFattura;

	private Long numero;
	
	private Integer anno;

	private BigDecimal importo;
	
	private String statoFattura;

	public Fattura(Cliente cliente, LocalDate data, Long numero, Integer anno, BigDecimal importo, String stato) {
		this.cliente = cliente;
		this.dataFattura = data;
		this.numero = numero;
		this.anno = anno;
		this.importo = importo;
		this.statoFattura = stato;
	
		
	}

}
