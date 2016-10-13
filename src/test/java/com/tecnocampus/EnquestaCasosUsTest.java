package com.tecnocampus;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.EnquestaDuplicadaException;
import com.tecnocampus.useCases.EnquestaCasosUs;
import com.tecnocampus.useCases.PreguntaCasosUs;
import com.tecnocampus.useCases.RespostaCasosUs;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.junit.Before;
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
    @Autowired
    PreguntaCasosUs preguntaCasosUs;
    @Autowired
    RespostaCasosUs respostaCasosUs;
    @Autowired
    UsuariCasosUs usuariCasosUs;

    Usuari usuari;
    Enquesta enquesta;

    @Before
    public void before(){

    }

    @Test
    @Transactional
    public void crearEnquestaTest(){
        //Creem l'enquesta
        Enquesta enquesta = enquestaCasosUs.crearEnquesta("Els serveis");
        Enquesta enquestaDB = enquestaCasosUs.obetenirEnquesta(enquesta.getId());
        Assert.isTrue(enquestaDB.getTitol().equalsIgnoreCase(enquesta.getTitol()));
    }

    @Test
    @Transactional
    public void crearEnquestaDuplicadaTest(){
        expectedEx.expect(EnquestaDuplicadaException.class);
        Enquesta enquesta1 = enquestaCasosUs.crearEnquesta("Els serveis");
        Enquesta enquesta2 = enquestaCasosUs.crearEnquesta("Els serveis");
    }

    @Test
    @Transactional
    public void guardarEnquestaDuplicadaTest(){
        expectedEx.expect(EnquestaDuplicadaException.class);

        Enquesta enquesta1 = enquestaCasosUs.obetenirEnquesta(1L);
        Enquesta enquesta2 = enquestaCasosUs.obetenirEnquesta(2L);
        enquesta1.setTitol(enquesta2.getTitol());
        enquestaCasosUs.save(enquesta1);
    }

    @Test
    @Transactional
    public void guardarEnquestaTest(){
        String enquestaTitol;

        Enquesta enquesta1 = enquestaCasosUs.obetenirEnquesta(1L);
        enquestaTitol = enquesta1.getTitol() + " 2";
        enquesta1.setTitol(enquestaTitol);
        enquestaCasosUs.save(enquesta1);

        Enquesta enquestaDB = enquestaCasosUs.obetenirEnquesta(1L);
        Assert.isTrue(enquestaDB.getTitol().equalsIgnoreCase( enquestaTitol));

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
