package com.energy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class LoginRequest {
	
	private String userName;
	private String password;

}
