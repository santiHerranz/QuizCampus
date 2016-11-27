package com.tecnocampus;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.ContrasenyaNoValidaException;
import com.tecnocampus.exceptions.UsuariDuplicatException;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UsuariCasosUsTest {

	@Rule // Aplica a tota la classe
	public ExpectedException expectedEx = ExpectedException.none();

	@Autowired
	UsuariCasosUs usuariCasosUs;

	@Test
	public void crearUsuariTest(){
		//creem l'usuari
		Usuari testAdmin = usuariCasosUs.crearUsuari("ADMIN","qwertyuioP53");
		//cerquem per email
		Usuari usuari = usuariCasosUs.cercarUsuari(testAdmin.getUsername());
		// els dos usuaris han de tenir el mateix id
		Assert.assertTrue(testAdmin.getId().equals(usuari.getId()));
	}

	@Test
	public void crearUsuariRepetitTest(){
		expectedEx.expect(UsuariDuplicatException.class);

		Usuari usuari1 = usuariCasosUs.crearUsuari("usuari@domini.com","123Ig45aatt");
		Usuari usuari2 = usuariCasosUs.crearUsuari("usuari@domini.com","123Ig45aatt");
	}

	@Test
	public void eliminarUsuariTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(2L);
		usuariCasosUs.eliminarUsuari(usuari);
		usuari = usuariCasosUs.cercarUsuari(2L);
		Assert.assertNull(usuari);
	}

	@Test
	public void NoEsPotDegradarUnUusariTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(2L);

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("L'usuari no és administrador, no es pot degradar!!!");

		usuariCasosUs.degradarAdmin(usuari);
	}

	@Test
	public void comprobarContrasenyaTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(1L);
		String username = usuari.getUsername();
		String password = usuari.getPassword();
		Boolean result = usuariCasosUs.comprobarContrasenya(username, password);
		Assert.assertTrue(result);
	}

	@Test
	public void comprobarContrasenyaNullTest(){
		expectedEx.expect(IllegalArgumentException.class);
		Boolean result = usuariCasosUs.comprobarContrasenya(null, null);
	}

	@Test
	public void comprobarContrasenyaUsuariNoExisteixTest(){
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("Username no trobat!");
		Boolean result = usuariCasosUs.comprobarContrasenya("noone@domail.com", "123456789");
	}



	@Test
	public void canviarEmailDuplicatTest(){
		expectedEx.expect(UsuariDuplicatException.class);

		Usuari usuari1 = usuariCasosUs.cercarUsuari(1L);
		Usuari usuari2 = usuariCasosUs.cercarUsuari(2L);
		usuari1.setUsername(usuari2.getUsername());
		usuariCasosUs.save(usuari1);
	}


/*
	@Test
	public void degradarAdminTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(2L);
		usuari= usuariCasosUs.promocionarAdmin(usuari);
		usuari = usuariCasosUs.cercarUsuari(1L);
		usuari = usuariCasosUs.promocionarAdmin(usuari);
		usuari = usuariCasosUs.degradarAdmin(usuari);
	}
*/



	@Test
	public void NoEsPotEliminarUltimAdminTest(){
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("L'usuari és l'ultim administrador, no es pot eliminar!!!");

		for (Usuari u : usuariCasosUs.llistarUsuaris() ) {
			usuariCasosUs.eliminarUsuari(u);
		}
	}


	@Test
	public void llistarUsuarisTest(){
		List<Usuari> usuaris = usuariCasosUs.llistarUsuaris();
		Assert.assertTrue(usuaris.size() > 0);
	}

	@Test
	public void ContrasenyaLongitud(){
		expectedEx.expect(ContrasenyaNoValidaException.class);
		Usuari usuariTest = usuariCasosUs.crearUsuari("mail@tecnocampus.cat", "gh12");
	}

	@Test
	public void ContrasenyaNumeros(){
		expectedEx.expect(ContrasenyaNoValidaException.class);
		Usuari usuariTest = usuariCasosUs.crearUsuari("mail@tecnocampus.cat", "ghbansjfbdh");
	}

	@Test
	public void ConstrasenyaMajuscules(){
		expectedEx.expect(ContrasenyaNoValidaException.class);
		Usuari usuariTest = usuariCasosUs.crearUsuari("mail@tecnocampus.cat","gh12dfgfgffg");
	}

	@Test
	public void ContrasenyaMinuscules(){
		expectedEx.expect(ContrasenyaNoValidaException.class);
		Usuari usuariTest = usuariCasosUs.crearUsuari("mail@tecnocampus.cat", "KLAGSFDJAHL12");
	}

	@Test
	public void ContrasenyaNoEspaisBlancs(){
		expectedEx.expect(ContrasenyaNoValidaException.class);
		Usuari usuariTest = usuariCasosUs.crearUsuari("mail@tecnocampus.cat", "ghIG12 fsdyg");
	}

}
