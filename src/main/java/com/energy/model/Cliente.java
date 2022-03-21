package com.energy.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ragioneSociale;

	private String partitaIva;
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;

	private String email;
	private String pec;

	private String telefono;
	private String nomeContatto;
	private String cognomeContatto;
	private String telefonoContatto;
	private String emailContatto;
	
	@OneToOne(cascade = { CascadeType.MERGE,CascadeType.REMOVE, CascadeType.REFRESH},fetch = FetchType.EAGER)
	private Indirizzo indirizzoSedeOperativa;
	
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH},fetch = FetchType.EAGER)
	private Indirizzo indirizzoSedeLegale;
	
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	private BigDecimal fatturatoAnnuale;
	
	@OneToMany( mappedBy = "cliente", cascade = { CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER )
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private List<Fattura> fatture=new ArrayList<>();

	public Cliente(String ragioneSociale, String partitaIva, TipoCliente tipoCliente, String email, String pec,
			String telefono, String nomeContatto, String cognomeContatto, String telefonoContatto, String emailContatto,
			Indirizzo indirizzoSedeOperativa, Indirizzo indirizzoSedeLegale, LocalDate dataInserimento,
			LocalDate dataUltimoContatto) {
		
		this.ragioneSociale = ragioneSociale;
		this.partitaIva = partitaIva;
		this.tipoCliente = tipoCliente;
		this.email = email;
		this.pec = pec;
		this.telefono = telefono;
		this.nomeContatto = nomeContatto;
		this.cognomeContatto = cognomeContatto;
		this.telefonoContatto = telefonoContatto;
		this.emailContatto = emailContatto;
		this.indirizzoSedeOperativa = indirizzoSedeOperativa;
		this.indirizzoSedeLegale = indirizzoSedeLegale;
		this.dataInserimento = dataInserimento;
		this.dataUltimoContatto = dataUltimoContatto;
		
		this.fatturatoAnnuale = new BigDecimal("0");
	}
	

}
