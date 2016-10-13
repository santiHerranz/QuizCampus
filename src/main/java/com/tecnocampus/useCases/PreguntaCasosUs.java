package com.tecnocampus.useCases;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.*;
import com.tecnocampus.exceptions.PreguntaNoExisteixException;
import com.tecnocampus.exceptions.RespostaDuplicadaException;
import com.tecnocampus.exceptions.RespostaForaDeLimitsException;
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
    BeansManager beansManager;

    public PreguntaCasosUs() {}

    public Resposta afegirResposta(Pregunta pregunta, Usuari usuari, int valor) throws RuntimeException {

        if(usuari == null)
            throw new IllegalArgumentException("usuari");
        if(pregunta == null)
            throw new IllegalArgumentException("pregunta");

        // Un usuari no pot respondre la mateixa pregunta 2 cops
        if (!beansManager.respostaRepository.canAnswer(pregunta, usuari))
            throw new RespostaDuplicadaException("Un usuari no pot respondre la mateixa pregunta 2 cops");

        if(valor < ((PreguntaNumerica)pregunta).getMinim())
            throw new RespostaForaDeLimitsException("El valor de la resposta no pot ser inferior el valor mínim de la pregunta");
        if(valor > ((PreguntaNumerica)pregunta).getMaxim())
            throw new RespostaForaDeLimitsException("El valor de la resposta no pot superar el valor màxim de la pregunta");

        RespostaNumerica resposta = new RespostaNumerica();

        resposta.setValor(valor);

        resposta.setUsuari(usuari);
        usuari.afegirResposta(resposta);

        pregunta.afegirResposta(resposta);
        resposta.setPregunta(pregunta);

        beansManager.respostaRepository.save(pregunta, resposta);
        return resposta;

    }

    /***
     *
     * @param pregunta
     * @return
     * @throws Exception
     */
    public void eliminarPregunta(Pregunta pregunta) throws RuntimeException {
        if (beansManager.preguntaRepository.findOne(pregunta.getId()) != null)
            throw new PreguntaNoExisteixException();
        beansManager.preguntaRepository.delete(pregunta);
    }

    /***
     *
     * @param enquesta
     * @throws Exception
     */
    public void eliminarTotesPreguntes(Enquesta enquesta) throws RuntimeException {
        for (Pregunta pregunta: enquesta.getPreguntes()) {
            eliminarPregunta(pregunta);
        }
    }

    public List<Pregunta> llistarPreguntes() {
        return beansManager.preguntaRepository.findAll();
    }
}
