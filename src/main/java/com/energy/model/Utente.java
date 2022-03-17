package com.energy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_spring") // la tabella va rinominata cosi perché su postgres la tabella user non si può fare
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private String userName;
	private String password;
	private String email;
	private boolean isActive = true;
	
	public Utente(String username,String email, String password, String nome,String cognome) {
		super();
		this.userName = username;
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
	}
	
	@ManyToMany
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

}
