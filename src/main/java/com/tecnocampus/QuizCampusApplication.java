package com.tecnocampus;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.useCases.EnquestaCasosUs;
import com.tecnocampus.useCases.PreguntaCasosUs;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuizCampusApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizCampusApplication.class, args);
	}

	@Autowired
	private UsuariCasosUs userUseCases;


	@Autowired
	private EnquestaCasosUs enquestaCasosUs;

	@Autowired
	private PreguntaCasosUs preguntaCasosUs;

	// Acces a BBDD
	// http://localhost:8080/h2-console
	// h2:mem:testdb

	@Bean
	CommandLineRunner runner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {

				Usuari usuari = userUseCases.crearUsuari("sherranzm", "112233");

				Usuari iargemi = userUseCases.crearUsuari("iargemi", "112233");

				System.out.println("Llistat usuaris");
				userUseCases.llistarUsuaris().forEach(u -> System.out.println(
						u.getId() +" "+ u.getEmail() +" "+ u.getContrasenya() +" "+ u.isAdmin()
				));

				System.out.println("comprobarContrasenya "+ userUseCases.comprobarContrasenya("112233",usuari));


				try{
					userUseCases.eliminarUsuari(usuari);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}


				userUseCases.ferAdmin(iargemi);

				try{
					userUseCases.desferAdmin(usuari);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}


				try{
					userUseCases.eliminarUsuari(usuari);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				System.out.println("Llistat usuaris");
				userUseCases.llistarUsuaris().forEach(u -> System.out.println(
						u.getId() +" "+ u.getEmail() +" "+ u.getContrasenya()+" "+ u.isAdmin()
				));


				////////////////////////////



				Enquesta enquesta = enquestaCasosUs.crearEnquesta("Nova enquesta");

				preguntaCasosUs.crearPreguntaNumerica(enquesta, "Primera pregunta", 1, 10);
				preguntaCasosUs.crearPreguntaNumerica(enquesta, "Segona pregunta", 1, 10);



				System.out.println("Llistat d'enquestes");
				enquestaCasosUs.llistarEnquestes().forEach(e ->  System.out.println(e));


				System.out.println("Llistat de preguntes");
				preguntaCasosUs.llistarPreguntes().forEach(u -> System.out.println(u));

				for(Enquesta e : enquestaCasosUs.llistarEnquestes()){
					System.out.format("Enquesta %s %n", e);
					for(Pregunta p : e.getPreguntes()){
						System.out.format("Pregunta %s %n", p);
						for(Resposta r : p.getRespostes()){
							System.out.format("Resposta %s %n", r);
						}
					}
				}


			}
		};
	}

}
