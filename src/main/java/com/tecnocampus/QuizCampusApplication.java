package com.tecnocampus;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.useCases.UserUseCases;
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
	private UserUseCases userUseCases;

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




			}
		};
	}

}
