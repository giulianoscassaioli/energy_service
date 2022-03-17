package com.energy.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Indirizzo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String via;
	private int civico;
	private String cap;
	private String localita;
	
	@ManyToOne(cascade = { CascadeType.DETACH,CascadeType.REFRESH
                                       }, fetch = FetchType.EAGER)
	private Comune comune;

	public Indirizzo(String via, int civico, String cap, String localita, Comune comune) {
		this.via = via;
		this.civico = civico;
		this.cap = cap;
		this.localita = localita;
		this.comune = comune;
	}

}
