package com.tecnocampus;


import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.useCases.EnquestaCasosUs;
import com.tecnocampus.useCases.PreguntaCasosUs;
import com.tecnocampus.useCases.RespostaCasosUs;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ThreadLocalRandom;


@SpringBootApplication
public class QuizCampusApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizCampusApplication.class, args);
	}

	@Autowired
	private UsuariCasosUs usuariCasosUs;

	@Autowired
	private RespostaCasosUs respostaCasosUs;

	@Autowired
	private PreguntaCasosUs preguntaCasosUs;

	@Autowired
	private EnquestaCasosUs enquestaCasosUs;

	// Acces a BBDD
	// http://localhost:8080/h2-console
	// h2:mem:testdb

	@Bean
	CommandLineRunner runner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {

				System.out.println("Quiz Campus Online");

				createDemoData();

			}
		};
	}

	private void createDemoData() {

		Usuari demo = usuariCasosUs.cercarUsuari("demo");

		Enquesta enquesta = enquestaCasosUs.crearEnquesta("Nova");
		enquestaCasosUs.afegirPregunta( enquesta, "1. Pregunta 1", 1, 5);
		enquestaCasosUs.afegirPregunta( enquesta, "2. Pregunta 2", 1, 5);
		enquestaCasosUs.afegirPregunta( enquesta, "3. Pregunta 3", 1, 5);

		for ( int i=0; i<1000; i++) {
			for (Pregunta p: enquesta.getPreguntes()) {
				enquestaCasosUs.afegirResposta(p, demo, ThreadLocalRandom.current().nextInt(1,5+1));
			}
		}

	}



}
