package com.tecnocampus;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.PreguntaNoExisteixException;
import com.tecnocampus.exceptions.RespostaForaDeLimitsException;
import com.tecnocampus.useCases.EnquestaCasosUs;
import com.tecnocampus.useCases.PreguntaCasosUs;
import com.tecnocampus.useCases.RespostaCasosUs;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.junit.Assert;
import org.junit.Before;
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

    Usuari usuari;
    Enquesta enquesta;

    @Before
    public void before(){
        usuari = usuariCasosUs.cercarUsuari(1L);
        enquesta = enquestaCasosUs.obtenirEnquesta(1L);
    }


    /***
     * Prova bàsica per respondre una pregunta de l'enquesta
     */
    @Test
    @Transactional
    public void respondrePreguntaEnquestaTest(){

        Pregunta p = enquestaCasosUs.afegirPregunta(enquesta, "Pregunta nova", 1, 10);
        Resposta r = preguntaCasosUs.afegirResposta(p, usuari, 10);

        Assert.assertEquals(r.toString(), respostaCasosUs.obtenirResposta(r.getId()).toString());

    }
    @Test
    @Transactional
    public void respondrePreguntaForaLimitSuperiorTest(){
        expectedEx.expect(RespostaForaDeLimitsException.class);

        Pregunta p = enquestaCasosUs.afegirPregunta(enquesta, "Pregunta nova", 1, 10);
        Resposta r = preguntaCasosUs.afegirResposta(p, usuari, 15);
    }
    @Test
    @Transactional
    public void respondrePreguntaForaLimitInferiorTest(){
        expectedEx.expect(RespostaForaDeLimitsException.class);

        Pregunta p = enquestaCasosUs.afegirPregunta(enquesta, "Pregunta nova", 1, 10);
        Resposta r = preguntaCasosUs.afegirResposta(p, usuari, -1);
    }

    @Test
    @Transactional
    public void respondrePreguntaArgumentsNullTest(){
        expectedEx.expect(IllegalArgumentException.class);

        Resposta r = preguntaCasosUs.afegirResposta(null, null, 0);
    }


    @Test
    @Transactional
    public void eliminarPreguntaTest(){
        Pregunta p = enquestaCasosUs.afegirPregunta(enquesta, "Pregunta nova", 1, 10);
        preguntaCasosUs.eliminarPregunta(p);
    }

    @Test
    @Transactional
    public void eliminarPreguntaNoExisteixTest(){
        expectedEx.expect(PreguntaNoExisteixException.class);

        Pregunta p = enquestaCasosUs.afegirPregunta(enquesta, "Pregunta nova", 1, 10);
        preguntaCasosUs.eliminarPregunta(p);
        preguntaCasosUs.eliminarPregunta(p);
    }




}
