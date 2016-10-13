package com.tecnocampus;

import com.tecnocampus.useCases.EnquestaCasosUs;
import com.tecnocampus.useCases.PreguntaCasosUs;
import com.tecnocampus.useCases.RespostaCasosUs;
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

			}
		};
	}

}
