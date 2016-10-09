package com.tecnocampus.useCases;

import com.tecnocampus.databaseRepositories.EnquestaRepository;
import com.tecnocampus.databaseRepositories.PreguntaRepository;
import com.tecnocampus.databaseRepositories.RespostaRepository;
import com.tecnocampus.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by santi on 6/10/16.
 *
 * # Casos d'us per les preguntes
 *
 * - Afegir pregunta a l'enquesta
 * - Eliminar una pregunta
 * - Eliminar totes les preguntes de l'enquesta
 * -
 *
 */

@Service
public class PreguntaCasosUs {

    @Autowired
    PreguntaRepository preguntaRepository;

    @Autowired
    RespostaRepository respostaRepository;

    public PreguntaCasosUs() {}

/*
    public PreguntaCasosUs(PreguntaRepository preguntaRepository,RespostaRepository respostaRepository,EnquestaRepository enquestaRepository) {
        this.preguntaRepository = preguntaRepository;
        this.respostaRepository = respostaRepository;
        this.enquestaRepository = enquestaRepository;
    }
*/


    public Resposta afegirResposta(Pregunta pregunta, Usuari usuari, int valor) throws Exception {

        // Un usuari no pot respondre la mateixa pregunta 2 cops
        if (!respostaRepository.canAnswer(pregunta, usuari))
            throw new Exception("Un usuari no pot respondre la mateixa pregunta 2 cops");

        RespostaNumerica resposta = new RespostaNumerica();
        resposta.setUsuari(usuari);
        resposta.setValor(valor);
        pregunta.afegirResposta(resposta);
        respostaRepository.save(pregunta, resposta);
        return resposta;

    }

    /***
     *
     * @param pregunta
     * @return
     * @throws Exception
     */
    public int eliminarPregunta(Pregunta pregunta) throws Exception {
        return preguntaRepository.delete(pregunta);
    }

    /***
     *
     * @param enquesta
     * @throws Exception
     */
    public void eliminarTotesPreguntes(Enquesta enquesta) throws Exception {
        for (Pregunta pregunta: enquesta.getPreguntes()) {
            eliminarPregunta(pregunta);
        }
    }

    public List<Pregunta> llistarPreguntes() {
        return preguntaRepository.findAll();
    }
}
