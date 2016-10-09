package com.tecnocampus.useCases;

import com.tecnocampus.domain.*;
import com.tecnocampus.managers.PreguntaRepository;
import com.tecnocampus.managers.RespostaRepository;
import org.springframework.stereotype.Service;

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
    private PreguntaRepository preguntaRepository;
    private RespostaRepository respostaRepository;

    public PreguntaCasosUs(PreguntaRepository preguntaRepository,RespostaRepository respostaRepository) {
        this.preguntaRepository = preguntaRepository;
        this.respostaRepository = respostaRepository;
    }


    public Pregunta afegirResposta(Pregunta pregunta, Usuari usuari, int valor) {
        RespostaNumerica resposta = new RespostaNumerica();
        resposta.setUsuari(usuari);
        resposta.setValor(valor);
        pregunta.afegirResposta(resposta);
        respostaRepository.save(pregunta, resposta);
        return pregunta;
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

    public Iterable<Pregunta> llistarPreguntes() {
        return preguntaRepository.findAll();
    }
}
