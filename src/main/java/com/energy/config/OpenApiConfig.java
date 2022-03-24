package com.energy.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Customer Management Service API", version = "v1"
,description = "Questo applicativo serve per gestire il fatturato dei clienti iscritti al"
		+ " serivizio. Le entita coinvolte in questo servizio sono appunto dei Clienti, le loro Fatture, gli Indirizzi "
		+ " dei clienti, ed una lista di  oltre 7000 Comuni con relative Provincie italiane realmente esistenti. Prima di poter effettuare qualsiasi "
		+ " operazione andare in fondo alla pagina per iscriversi ed"
		+ " effettuare il login. Inserire successivamente il Bearer token ricevuto come risposta utilizzando il pulsante"
		+ " Authorize"
		+ " Le richieste POST,PUT e DELETE sono disponibili soltanto per gli utenti iscritti col ruolo admin, per gli utenti"
		+ " con ruolo user sono diponibili soltanto le richieste GET"))
@SecurityScheme(
		
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		
)

public class OpenApiConfig {

}
