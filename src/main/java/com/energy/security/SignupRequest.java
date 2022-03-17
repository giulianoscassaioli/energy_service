package com.energy.security;

import java.util.Set;

import javax.validation.constraints.Email;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class SignupRequest {
    private String username;
    @Email
    private String email;
    private Set<String> role;
    private String password;
    private String nome;
    private String cognome;
    
    
}
