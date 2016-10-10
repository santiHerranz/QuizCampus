package com.tecnocampus;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.assertj.core.api.AssertProvider;
import org.hibernate.validator.cfg.defs.AssertTrueDef;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
		Usuari testAdmin = usuariCasosUs.crearUsuari("ADMIN","12345");
		//cerquem per email
		Usuari usuari = usuariCasosUs.cercarUsuari(testAdmin.getEmail());
		// els dos usuaris han de tenir el mateix id
		Assert.isTrue(testAdmin.getId().equals(usuari.getId()));
	}

	@Test
	public void NoEsPotCrearUsuariEmailRepetitTest(){
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("No es pot guardar l'usuari, l'email ja existeix!");

		Usuari testUser1 = usuariCasosUs.crearUsuari("user@domain.com","12345");
		Usuari testUser2 = usuariCasosUs.crearUsuari("user@domain.com","12345");
	}

	@Test
	public void eliminarUsuariTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(1L);
		usuariCasosUs.eliminarUsuari(usuari);
		usuari = usuariCasosUs.cercarUsuari(1L);
		Assert.isNull(usuari);
	}

	@Test
	public void NoEsPotDegradarUnUusariTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(1L);

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("L'usuari no és administrador, no es pot degradar!!!");

		usuariCasosUs.degradarAdmin(usuari);
	}

	@Test
	public void comprobarContrasenyaTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(1L);
		String email = usuari.getEmail();
		String password = usuari.getContrasenya();
		Boolean result = usuariCasosUs.comprobarContrasenya(email, password);
		Assert.isTrue(result);
	}

	@Test
	public void comprobarContrasenyaNullTest(){
		expectedEx.expect(IllegalArgumentException.class);
		Boolean result = usuariCasosUs.comprobarContrasenya(null, null);
	}

	@Test
	public void comprobarContrasenyaNoUsuariTest(){
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("Email no trobat!");
		Boolean result = usuariCasosUs.comprobarContrasenya("noone@domail.com", "123456789");
	}


	@Test
	public void NoEsPotDegradarUltimAdminTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(1L);
		usuariCasosUs.promocionarAdmin(usuari);

		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("L'usuari és l'ultim administrador, no es pot degradar!!!");

		usuariCasosUs.degradarAdmin(usuari);
	}

	@Test
	public void degradarAdminTest(){
		Usuari usuari = usuariCasosUs.cercarUsuari(2L);
		usuariCasosUs.promocionarAdmin(usuari);
		usuari = usuariCasosUs.cercarUsuari(1L);
		usuariCasosUs.promocionarAdmin(usuari);
		usuariCasosUs.degradarAdmin(usuari);
	}



	@Test
	public void NoEsPotEliminarUltimAdminTest(){
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("L'usuari és l'ultim administrador, no es pot eliminar!!!");

		Usuari testAdmin = usuariCasosUs.crearUsuari("ADMIN","12345");
		usuariCasosUs.promocionarAdmin(testAdmin);

		usuariCasosUs.eliminarUsuari(testAdmin);
	}

}
