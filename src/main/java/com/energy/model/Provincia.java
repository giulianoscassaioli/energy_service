package com.energy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Provincia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sigla;
	private String regione;

	public Provincia(String sigla, String nome, String regione) {
		
		this.sigla = sigla;
		this.nome = nome;
		this.regione = regione;
	}

}
