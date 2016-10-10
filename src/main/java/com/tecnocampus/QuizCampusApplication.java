package com.tecnocampus;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.Resposta;
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

				Usuari u1 = usuariCasosUs.crearUsuari("sherranzm", "112233");
				Usuari u2 = usuariCasosUs.crearUsuari("iargemi", "112233");

				System.out.println("Llistat usuaris");
				usuariCasosUs.llistarUsuaris().forEach(u -> System.out.println(
						u.getId() +" "+ u.getEmail() +" "+ u.getContrasenya() +" "+ u.isAdmin()
				));

				System.out.println("comprobarContrasenya "+ usuariCasosUs.comprobarContrasenya("112233",u1));

/*

				try{
					usuariCasosUs.eliminarUsuari(u1);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}


				usuariCasosUs.ferAdmin(u2);

				try{
					usuariCasosUs.desferAdmin(u1);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}


				try{
					usuariCasosUs.eliminarUsuari(u1);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				System.out.println("Llistat usuaris");
				usuariCasosUs.llistarUsuaris().forEach(u -> System.out.println(
						u.getId() +" "+ u.getEmail() +" "+ u.getContrasenya()+" "+ u.isAdmin()
				));



				////////////////////////////

				System.out.format("====%nLlistat d'enquestes%n");
				enquestaCasosUs.llistarEnquestes().forEach(e ->  System.out.println(e));

				System.out.format("====%nLlistat de preguntes%n");
				preguntaCasosUs.llistarPreguntes().forEach(p -> System.out.println(p));

				System.out.format("====%nLlistat de respostes%n");
				respostaCasosUs.llistarRespostes().forEach(r -> System.out.println(r));

*/
				System.out.format("====%nJerarquia Enquesta-Pregunta-Resposta%n");
				Enquesta enquesta = enquestaCasosUs.crearEnquesta("Nova enquesta");

				Pregunta p1 = enquestaCasosUs.afegirPregunta(enquesta,  "Primera pregunta", 1, 10);
				Pregunta p2 = enquestaCasosUs.afegirPregunta(enquesta,  "Segona pregunta", 1, 10);

				preguntaCasosUs.afegirResposta(p1, u1, 5);
				preguntaCasosUs.afegirResposta(p2, u1, 7);

				preguntaCasosUs.afegirResposta(p1, u2, 9);
				Resposta r1 = preguntaCasosUs.afegirResposta(p2, u2, 8);


				//preguntaCasosUs.afegirResposta(p2, u2, 8);

				for(Enquesta e : enquestaCasosUs.llistarEnquestes()){
					System.out.format("%s %n", e.toString());
					for(Pregunta p : e.getPreguntes()){
						System.out.format(" %s %n", p.toString());
						for(Resposta r : p.getRespostes()){
							System.out.format("  %s %n", r.toString());
						}
					}
				}

				System.out.format("====%nDATABASE %n");
				enquestaCasosUs.save(enquesta);
				Enquesta enquestaBD = enquestaCasosUs.llistarEnquestes().get(1);
				for(Pregunta p : enquestaBD.getPreguntes()){
					System.out.format(" %s %n", p.toString());
					for(Resposta r : p.getRespostes()){
						System.out.format("  %s %n", r.toString());
					}
				}

				System.out.format("====%nDATABASE PREGUNTA %n");
				Pregunta preguntaDB = preguntaCasosUs.llistarPreguntes().get(1);
				System.out.format(" %s %n", preguntaDB.toString());

				System.out.format("====%nDATABASE RESPOSTA %n");
				Resposta respostaDB = respostaCasosUs.llistarRespostes().get(1);
				System.out.format(" %s %n", respostaDB.toString());



				System.out.format("====%nDATABASE USUARI %n");
				Usuari usuariDB = usuariCasosUs.llistarUsuaris().get(3);
				System.out.format(" %s %n", usuariDB.toString());



			}
		};
	}

}
