package com.tecnocampus.useCases;

import com.tecnocampus.BeansManager;
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
    BeansManager beansManager;

    public PreguntaCasosUs() {}

    public Resposta afegirResposta(Pregunta pregunta, Usuari usuari, int valor) throws Exception {

        if(usuari == null) throw new Exception("NullArgumentException");
        if(pregunta == null) throw new Exception("NullArgumentException");

        // Un usuari no pot respondre la mateixa pregunta 2 cops
        if (!beansManager.respostaRepository.canAnswer(pregunta, usuari))
            throw new Exception("Un usuari no pot respondre la mateixa pregunta 2 cops");

        RespostaNumerica resposta = new RespostaNumerica();
        resposta.setUsuari(usuari);
        resposta.setValor(valor);
        pregunta.afegirResposta(resposta);
        beansManager.respostaRepository.save(pregunta, resposta);
        return resposta;

    }

    /***
     *
     * @param pregunta
     * @return
     * @throws Exception
     */
    public int eliminarPregunta(Pregunta pregunta) throws Exception {
        return beansManager.preguntaRepository.delete(pregunta);
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
        return beansManager.preguntaRepository.findAll();
    }
}
