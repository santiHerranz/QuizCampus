package com.tecnocampus.useCases;

import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.PreguntaNumerica;
import com.tecnocampus.managers.PreguntaManager;

/**
 * Created by santi on 6/10/16.
 */
public class PreguntaCasosUs {
    private PreguntaManager preguntaManager;

    public PreguntaCasosUs(PreguntaManager preguntaManager) {
        this.preguntaManager = preguntaManager;
    }

    /***
     *
     * @param enunciat text de la pregunta
     * @param min valoració minima permesa
     * @param max valoració màxima permesa
     * @return
     */
    public Pregunta crearPreguntaNumerica(String enunciat, int min, int max) {
        Pregunta pregunta = new PreguntaNumerica(enunciat, min, max);
        return pregunta;
    }


}
