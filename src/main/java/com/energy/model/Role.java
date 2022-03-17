package com.energy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private Roles roleName;

}
