package com.tecnocampus;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.Usuari;
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

import java.util.List;


/**
 * Created by santi on 11/10/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RespostaCasosUsTest {

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
    public void llistarRespostesTest() {

        List<Resposta> list = respostaCasosUs.llistarRespostes();
        Assert.assertTrue(list.size()>0);
    }

    @Test
    public void esborrarRespostaPreguntaTest(){


        Enquesta e = enquestaCasosUs.obetenirEnquesta(1L);
        Pregunta p = enquestaCasosUs.afegirPregunta(e, "Test", 1, 10);
        Usuari usuari = usuariCasosUs.cercarUsuari(1L);

        Resposta r = preguntaCasosUs.afegirResposta(p, usuari, 5);

        respostaCasosUs.esborraResposta(r);
    }
}