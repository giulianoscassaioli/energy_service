package com.energy.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.energy.model.Comune;
import com.energy.model.Provincia;
import com.energy.model.Role;
import com.energy.model.Roles;
import com.energy.model.Utente;
import com.energy.repository.ComuneRepository;
import com.energy.repository.ProvinciaRepository;
import com.energy.repository.RoleRepository;
import com.energy.repository.UtenteRepository;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe utility per l'inizializzazione del database all'avvio
 * dell'applicazione
 *
 */
@Component
@Slf4j
public class ApplicationStartupRunner implements CommandLineRunner {


	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private ProvinciaRepository provinciaRepository;
	
	@Autowired
	private ComuneRepository comuneRepository;

	@Override
	public void run(String... args) throws Exception {

		
		
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

		Role roleAdmin = new Role();
		
		roleAdmin.setRoleName(Roles.ROLE_ADMIN);
		Role roleUser= new Role();
	
		roleUser.setRoleName(Roles.ROLE_USER);
		Utente user = new Utente();
		Set<Role> roles = new HashSet<>();
		roles.add(roleAdmin);
		user.setUserName("admin");
		user.setPassword(bCrypt.encode("admin"));
		user.setEmail("admin@domain.com");
		user.setRoles(roles);
		user.setActive(true);
        user.setNome("Giuliano");
        user.setCognome("Scassaioli");
		roleRepository.save(roleAdmin);
		roleRepository.save(roleUser);
		utenteRepository.save(user);
		
		initProvincia();
		initComune();

	}

	private void initComune() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("comuni-italiani.csv"));) {
			String[] values = null;
			csvReader.readNext(); // aggiungo questa linea di codice per far si che il reader salti la prima riga
									// del file csv
			Provincia provincia;
			while ((values = csvReader.readNext()) != null) {
                
				
				Optional<Provincia> provincia1 = provinciaRepository.findByNomeLike("%"+values[3]+"%");
                if(provincia1.isPresent()) {
				Comune comune= new Comune();
                comune.setProvincia(provincia1.get());
			    comune.setNome(values[2]);
				
				comuneRepository.save(comune);
               }

			}
		}
	}

	private void initProvincia() throws FileNotFoundException, IOException {
		try (CSVReader csvReader = new CSVReader(new FileReader("province-italiane.csv"));) {
			String[] values = null;
			csvReader.readNext(); // aggiungo questa linea di codice per far si che il reader salti la prima riga
									// del file csv
			while ((values = csvReader.readNext()) != null) {
				provinciaRepository.save(new Provincia(values[0],values[1],values[2]));
			}
		}
	}

	


}
