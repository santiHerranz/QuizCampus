package com.tecnocampus;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.managers.UsuariManager;
import com.tecnocampus.useCases.UserUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class QuizCampusApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizCampusApplication.class, args);
	}

	@Autowired
	private UsuariManager usuariManager;

	@Autowired
	private UserUseCases userUseCases;

	@Bean
	CommandLineRunner runner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {


				Usuari usuari = userUseCases.createUser("sherranzm", "112233");

				userUseCases.createUser("iargemi", "112233");

				Iterable<Usuari> usuaris = usuariManager.findAll();
				usuaris.forEach(u -> System.out.println(u.getEmail() +" "+ u.getContrasenya()));



			}
		};
	}

}
