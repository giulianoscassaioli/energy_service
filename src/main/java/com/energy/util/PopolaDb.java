package com.energy.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.energy.controller.AuthController;
import com.energy.model.Cliente;
import com.energy.model.Comune;
import com.energy.model.Fattura;
import com.energy.model.Indirizzo;
import com.energy.model.TipoCliente;
import com.energy.repository.ProvinciaRepository;
import com.energy.service.ClienteService;
import com.energy.service.ComuneService;
import com.energy.service.FatturaService;
import com.energy.service.IndirizzoService;

@Component
public class PopolaDb implements CommandLineRunner {

	@Autowired
	ClienteService cliente;
	@Autowired
	ComuneService comune;
	@Autowired
	FatturaService fattura;
	@Autowired
	IndirizzoService indirizzo;

	@Autowired
	ProvinciaRepository provincia;

	@Override
	public void run(String... args) throws Exception {

		Indirizzo i1 = new Indirizzo("Via Milano", 29, "98134", "Periferia", comune.getByNome("Chiusa di San Michele").get());
		Indirizzo i2 = new Indirizzo("Via Catania", 28, "98345", "Periferia", comune.getByNome("Givoletto").get());
		Indirizzo i3 = new Indirizzo("Via Primavera", 19, "95682", "Periferia", comune.getByNome("Monteu da Po").get());
		Indirizzo i4 = new Indirizzo("Via Autunno", 17, "87233", "Centro", comune.getByNome("Roure").get());
		Indirizzo i5 = new Indirizzo("Via Inverno", 110, "20050", "Periferia", comune.getByNome("Tavagnasco").get());
		Indirizzo i6 = new Indirizzo("Via Duomo", 135, "57020", "Centro", comune.getByNome("Moraro").get());
		Indirizzo i7 = new Indirizzo("Via Antonio da Legnago", 37, "89010", "Marittima", comune.getByNome("Turriaco").get());
		Indirizzo i8 = new Indirizzo("Via Nazario Sauro", 156, "20012", "Centro", comune.getByNome("Sesto al Reghena").get());
		Indirizzo i9 = new Indirizzo("Via Croce Rossa", 176, "19070", "Centro", comune.getByNome("Millesimo").get());
		Indirizzo i10 = new Indirizzo("Via Giovanni Amendola", 84, "26010", "Periferia",
				comune.getByNome("Valbrevenna").get());
		Indirizzo i11 = new Indirizzo("Via Santa Maria di Costantinopoli", 135, "46040", "Marittima",
				comune.getByNome("Argelato").get());
		Indirizzo i12 = new Indirizzo("Via Francesco Del Giudice", 161, "35586", "Montagna",
				comune.getByNome("Vicovaro").get());
		Indirizzo i13 = new Indirizzo("Via Giallo", 78, "35797", "Periferia", comune.getByNome("Roma").get());
		Indirizzo i14 = new Indirizzo("Via Arancione", 32, "29664", "Lungomare", comune.getByNome("Modena").get());

		
		indirizzo.save(i1);
		indirizzo.save(i2);
		indirizzo.save(i3);
		indirizzo.save(i4);
		indirizzo.save(i5);
		indirizzo.save(i6);
		indirizzo.save(i7);
		indirizzo.save(i8);
		indirizzo.save(i9);
		indirizzo.save(i10);
		indirizzo.save(i11);
		indirizzo.save(i12);
		indirizzo.save(i13);
		indirizzo.save(i14);
		

		Cliente c1 = new Cliente("Daewoo", "2762583", TipoCliente.SRL, "daewoo@gmail.com", "daewoo@pec.com", "16369",
				"Emma", "Pisano", "37125785", "emma@gmail.com", i1, i1, LocalDate.of(2008, 11, 02),
				LocalDate.of(2021, 11, 27));
		Cliente c2 = new Cliente("Brondi", "4267293", TipoCliente.SAS, "brondi@gmail.com", "brondi@pec.com",
				"94678", "Genoveffa", "Nord", "3734690", "genoveffa@gmail.com", i2, i2, LocalDate.of(2010, 12, 17),
				LocalDate.of(2023, 2, 1));
		Cliente c3 = new Cliente("Index", "2762598", TipoCliente.SRL, "index@gmail.com", "index@pec.com", "35965",
				"Dario", "Di Marco", "337125785", "dario@gmail.com", i3, i3, LocalDate.of(2000, 11, 27),
				LocalDate.of(2020, 6, 7));
		Cliente c4 = new Cliente("Indesit", "84872466", TipoCliente.SAS, "indesit@gmail.com", "indesit@pec.com", "8916369",
				"Marco", "Rossi", "32685543", "marco@gmail.com", i4, i4, LocalDate.of(2009, 2, 12),
				LocalDate.of(2018, 4, 21));
		Cliente c5 = new Cliente("Roventa", "215789", TipoCliente.SPA, "roventa@gmail.com", "roventai@pec.com", "74732",
				"Noemi", "Ambra", "38455379", "noemi@gmail.com", i5, i5, LocalDate.of(2007, 11, 27),
				LocalDate.of(2015, 11, 27));
		Cliente c6 = new Cliente("Rovagnati", "1578904", TipoCliente.PA, "rovagnati@gmail.com", "rovagnati@pec.com",
				"43226", "Francesco", "Oll", "39583829", "francesco@gmail.com", i6, i6, LocalDate.of(2004, 3, 25),
				LocalDate.of(2014, 4, 21));
		Cliente c7 = new Cliente("Fiat", "824689", TipoCliente.SRL, "fiat@gmail.com", "fiat@pec.com", "84335",
				"Peppe", "Bianco", "6327884", "peppe@gmail.com", i7, i7, LocalDate.of(2002, 6, 17),
				LocalDate.of(2006, 1, 3));
		Cliente c8 = new Cliente("Reanault", "173577", TipoCliente.SAS, "reanault@gmail.com", "reanault@pec.com", "24789",
				"Carmen", "Di Pietro", "39864357", "carmen@gmail.com", i8, i8, LocalDate.of(2009, 7, 7),
				LocalDate.of(2016, 11, 27));
		Cliente c9 = new Cliente("Bitservice", "4367843", TipoCliente.PA, "bitservice@gmail.com", "bitservice@pec.com", "1876369",
				"Natasha", "Viola", "37125785", "natasha@gmail.com", i9, i9, LocalDate.of(2004, 4, 17),
				LocalDate.of(2007, 8, 22));
		Cliente c10 = new Cliente("Tecnology", "657853", TipoCliente.PA, "tecnologya@gmail.com", "tecnology@pec.com",
				"9653400", "Katiusha", "Blu", "312356906", "katiusha@gmail.com", i10, i10, LocalDate.of(2001, 1, 3),
				LocalDate.of(2009, 12, 23));
		Cliente c11 = new Cliente("Enterprice", "9534679", TipoCliente.SPA, "enterprice@gmail.com", "enterprice@pec.com",
				"43322554", "Filippa", "Mollica", "3456754", "filippa@gmail.com", i11, i11, LocalDate.of(2021, 11, 27),
				LocalDate.of(2021, 11, 27));
		Cliente c12 = new Cliente("Ferrari", "246796", TipoCliente.SAS, "ferrari@gmail.com", "ferrari@pec.com", "125664",
				"Maria", "Arancia", "312468975", "maria@gmail.com", i12, i12, LocalDate.of(2001, 9, 11),
				LocalDate.of(2018, 4, 28));
		Cliente c13 = new Cliente("Lamborghini", "963678", TipoCliente.SRL, "lamborghini@gmail.com", "lamborghini@pec.com", "1636912",
				"Wanda", "Maximoff", "396543675", "wanda@gmail.com", i13, i13, LocalDate.of(2011, 5, 14),
				LocalDate.of(2012, 1, 21));
		Cliente c14 = new Cliente("Gelati", "683689", TipoCliente.SRL, "gelati@gmail.com", "gelati@pec.com", "1636954",
				"Shantal", "Pera", "37125785", "shantal@gmail.com", i14, i14, LocalDate.of(2002, 4, 19),
				LocalDate.of(2001, 5, 24));

		cliente.save(c1);
		cliente.save(c2);
		cliente.save(c3);
		cliente.save(c4);
		cliente.save(c5);
		cliente.save(c6);
		cliente.save(c7);
		cliente.save(c8);
		cliente.save(c9);
		cliente.save(c10);
		cliente.save(c11);
		cliente.save(c12);
		cliente.save(c13);
		cliente.save(c14);
		

		Fattura f1 = new Fattura(c1, LocalDate.of(2020, 2, 12), 146l, 2022, new BigDecimal("830.50"), "pagata");
		Fattura f2 = new Fattura(c2, LocalDate.of(2019, 3, 21), 28l, 2022, new BigDecimal("100.50"), "non pagata");
		Fattura f3 = new Fattura(c3, LocalDate.of(2007, 6, 11), 13l, 2022, new BigDecimal("23.50"), "in transanzione");
		Fattura f4 = new Fattura(c4, LocalDate.of(2011, 2, 13), 125l, 2022, new BigDecimal("900.50"), "pagata");
		Fattura f5 = new Fattura(c5, LocalDate.of(2000, 1, 19), 196l, 2022, new BigDecimal("3356.50"), "in attesa");
		Fattura f6 = new Fattura(c6, LocalDate.of(2012, 2, 23), 137l, 2022, new BigDecimal("8632.50"), "non solvibile");
		Fattura f7 = new Fattura(c7, LocalDate.of(2015, 9, 17), 104l, 2022, new BigDecimal("8765.50"), "pagata");
		Fattura f8 = new Fattura(c8, LocalDate.of(2019, 4, 19), 127l, 2022, new BigDecimal("1358.50"), "pagata");
		Fattura f9 = new Fattura(c9, LocalDate.of(2010, 6, 20), 150l, 2022, new BigDecimal("8643.50"), "non solvibile");
		Fattura f10 = new Fattura(c10, LocalDate.of(2019, 11, 21), 134l, 2022, new BigDecimal("3676.50"), "pagata");
		Fattura f11 = new Fattura(c11, LocalDate.of(2011, 10, 23), 61l, 2022, new BigDecimal("1367.50"), "in attesa");
		Fattura f12 = new Fattura(c12, LocalDate.of(2012, 7, 26), 135l, 2022, new BigDecimal("25.50"), "pagata");
		Fattura f13 = new Fattura(c13, LocalDate.of(2014, 1, 29), 169l, 2022, new BigDecimal("1954.50"),
				"in transanzione");
		Fattura f14 = new Fattura(c14, LocalDate.of(2010, 9, 16), 12l, 2020, new BigDecimal("1936.50"), "non pagata");
		
		Fattura f15 = new Fattura(c14, LocalDate.of(2020, 2, 12), 1465l, 2020, new BigDecimal("830.50"), "pagata");
		Fattura f16 = new Fattura(c14, LocalDate.of(2019, 3, 21), 288l, 2020, new BigDecimal("100.50"), "non pagata");
		Fattura f17 = new Fattura(c14, LocalDate.of(2007, 6, 11), 131l, 2020, new BigDecimal("23.50"), "in transanzione");
		Fattura f18 = new Fattura(c14, LocalDate.of(2011, 2, 13), 1254l, 2020, new BigDecimal("900.50"), "pagata");
		Fattura f19 = new Fattura(c14, LocalDate.of(2000, 1, 19), 1961l, 2020, new BigDecimal("3356.50"), "in attesa");
		Fattura f20 = new Fattura(c14, LocalDate.of(2012, 2, 23), 1374l, 2020, new BigDecimal("8632.50"), "non solvibile");
		Fattura f21 = new Fattura(c14, LocalDate.of(2015, 9, 17), 1044l, 2020, new BigDecimal("8765.50"), "pagata");
		Fattura f22 = new Fattura(c14, LocalDate.of(2019, 4, 19), 1275l, 2020, new BigDecimal("1358.50"), "pagata");
		Fattura f23 = new Fattura(c14, LocalDate.of(2010, 6, 20), 1505l, 2022, new BigDecimal("8643.50"), "non solvibile");
		Fattura f24 = new Fattura(c14, LocalDate.of(2019, 11, 21), 1345l, 2022, new BigDecimal("3676.50"), "pagata");
		Fattura f25 = new Fattura(c14, LocalDate.of(2011, 10, 23), 615l, 2022, new BigDecimal("1367.50"), "in attesa");
		Fattura f26 = new Fattura(c14, LocalDate.of(2012, 7, 26), 1355l, 2022, new BigDecimal("25.50"), "pagata");
		Fattura f27 = new Fattura(c14, LocalDate.of(2014, 1, 29), 1695l, 2022, new BigDecimal("1954.50"),
				"in transanzione");
		Fattura f28 = new Fattura(c14, LocalDate.of(2010, 9, 16), 1599l, 2022, new BigDecimal("1936.50"), "non pagata");

		Fattura f29 = new Fattura(c14, LocalDate.of(2014, 1, 29), 16905l, 2022, new BigDecimal("1954.50"),
				"in transanzione");
		Fattura f30 = new Fattura(c14, LocalDate.of(2010, 9, 16), 10325l, 2022, new BigDecimal("1936.50"), "non pagata");
		Fattura f31 = new Fattura(c14, LocalDate.of(2014, 1, 29), 1697l, 2022, new BigDecimal("1954.50"),
				"in transanzione");
		Fattura f32 = new Fattura(c14, LocalDate.of(2010, 9, 16), 13327l, 2022, new BigDecimal("1936.50"), "non pagata");
		
		fattura.save(f1);
		fattura.save(f2);
		fattura.save(f3);
		fattura.save(f4);
		fattura.save(f5);
		fattura.save(f6);
		fattura.save(f7);
		fattura.save(f8);
		fattura.save(f9);
		fattura.save(f10);
		fattura.save(f11);
		fattura.save(f12);
		fattura.save(f13);
		fattura.save(f14);
		
		

		fattura.save(f15);
		fattura.save(f16);
		fattura.save(f17);
		fattura.save(f18);
		fattura.save(f19);
		fattura.save(f20);
		fattura.save(f21);
		fattura.save(f22);
		fattura.save(f23);
		fattura.save(f24);
		fattura.save(f25);
		fattura.save(f26);
		fattura.save(f27);
		fattura.save(f28);
		fattura.save(f29);
		fattura.save(f30);
		
		fattura.save(f31);
		fattura.save(f32);
		

	}

}
