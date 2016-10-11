package com.tecnocampus;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.useCases.EnquestaCasosUs;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
 * Created by santi on 11/10/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EnquestaCasosUsTest {

    @Rule // Aplica a tota la classe
    public ExpectedException expectedEx = ExpectedException.none();

    @Autowired
    EnquestaCasosUs enquestaCasosUs;

    @Test
    public void crearEnquestaTest(){
        //Creem l'usuari
        Enquesta enquesta = enquestaCasosUs.crearEnquesta("Els serveis");
        Assert.isTrue(enquesta.getTitol().equalsIgnoreCase("Els serveis"));
    }
    @Test
    public void afegirPreguntaEnquestaTest(){
        //Assert.("Falta test");
    }
    @Test
    public void mourePreguntaEnquestaTest(){
        //Assert.fail("Falta test");
    }
    @Test
    public void esborrarPreguntaEnquestaTest(){
        //Assert.fail("Falta test");
    }
    @Test
    public void esborrarEnquestaTest(){
        //Assert.fail("Falta test");
    }
    @Test
    public void modificarEnquestaTest(){
        //Assert.fail("Falta test");
    }

}
