package com.energy.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.energy.exception.CrmException;
import com.energy.model.LoginRequest;
import com.energy.model.LoginResponse;
import com.energy.model.Role;
import com.energy.model.Roles;
import com.energy.model.Utente;
import com.energy.repository.RoleRepository;
import com.energy.repository.UtenteRepository;
import com.energy.security.JwtUtils;
import com.energy.security.SignupRequest;
import com.energy.security.SignupResponse;
import com.energy.service.UserDetailsImpl;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UtenteRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		LoginResponse loginResponse = new LoginResponse();
		
		loginResponse.setToken(token);
		loginResponse.setRoles(roles);
		
		return ResponseEntity.ok(loginResponse);
	}
	
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        // Vedo se lo Username e l'Email  non siano gia in uso
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new SignupResponse("Errore: Username già in uso!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new SignupResponse("Errore: Email già in uso!"));
        }
        // Crea un nuovo user criptando la password
        Utente user = new Utente(signUpRequest.getUsername(), signUpRequest.getEmail(),bCrypt.encode(signUpRequest.getPassword()), signUpRequest.getNome(),signUpRequest.getCognome());
       
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
     // Vedo se il role inserito sia corretto
        if (strRoles.isEmpty()) {
            Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER).orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
            roles.add(userRole);
        } else {
        	for(String role: strRoles) {
        		if(role.equals("")) {
        			Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER).orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
                    roles.add(userRole);
                    
        		}
        		else if(role.equalsIgnoreCase("admin")) {
        			Role adminRole = roleRepository.findByRoleName(Roles.ROLE_ADMIN).orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
                    roles.add(adminRole);
                    
        		}
        		else if(role.equalsIgnoreCase("user")) {
        		   Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER).orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
                   roles.add(userRole);
        			}
        	   else {
        		   throw new CrmException("Puoi inserire soltanto role 'admin' o 'user'!");
        	   }
        		}
        	
        	}
        	
  
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new SignupResponse("User registrato con successo!"));
    }
	
	@GetMapping("/modulosignup")
	public ModelAndView signup(){
	
		SignupRequest signUpRequest= new SignupRequest();
		return new ModelAndView("signup","signuprequest",signUpRequest);
	}
	
	
	@PostMapping("/loginform")
	public ModelAndView authenticateUserForm(LoginRequest loginRequest) {
		ModelAndView model = new ModelAndView();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		authentication.getAuthorities();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.setViewName("menu");
		return model;
	}

	@PostMapping("/signupform")
	public ModelAndView registerUserForm(SignupRequest signUpRequest) {
		ModelAndView model = new ModelAndView();
		if (userRepository.existsByUserName(signUpRequest.getUsername())) {
			model.setViewName("errore");
			return model;
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			model.setViewName("errore");
			return model;
		}
		Utente user = new Utente(signUpRequest.getUsername(), signUpRequest.getEmail(),
				bCrypt.encode(signUpRequest.getPassword()), signUpRequest.getNome(), signUpRequest.getCognome());
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		// Verifica l'esistenza dei Role
		if (strRoles==null) {
			Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
			roles.add(userRole);
		} else {
			for(String role: strRoles) {
        		if(role.equals("")) {
        			Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER).orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
                    roles.add(userRole);
                    
        		}
        		else if(role.equalsIgnoreCase("admin")) {
        			Role adminRole = roleRepository.findByRoleName(Roles.ROLE_ADMIN).orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
                    roles.add(adminRole);
                    
        		}
        		else if(role.equalsIgnoreCase("user")) {
        		   Role userRole = roleRepository.findByRoleName(Roles.ROLE_USER).orElseThrow(() -> new CrmException("Errore: Role non trovato!"));
                   roles.add(userRole);
        			}
        	   else {
        		   throw new CrmException("Puoi inserire soltanto role 'admin' o 'user'!");
        	   }
        		}
		}
		user.setRoles(roles);
		userRepository.save(user);
		authenticateUserForm(new LoginRequest(signUpRequest.getUsername(), signUpRequest.getPassword()));
		model.setViewName("index");
		return model;
	}

	

}
