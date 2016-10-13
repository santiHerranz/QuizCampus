package com.tecnocampus;

import com.tecnocampus.useCases.EnquestaCasosUs;
import com.tecnocampus.useCases.PreguntaCasosUs;
import com.tecnocampus.useCases.RespostaCasosUs;
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


/**
 * Created by santi on 11/10/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PreguntaCasosUsTest {

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

    @Test
    public void crearPreguntaTest(){
        Assert.fail("Falta test");
    }
    @Test
    public void eliminarPreguntaTest(){
        Assert.fail("Falta test");
    }
    @Test
    public void respondrePreguntaEnquestaTest(){
        Assert.fail("Falta test");
    }


}
