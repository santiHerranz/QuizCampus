package com.tecnocampus.useCases;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.PreguntaNumerica;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.managers.PreguntaManager;

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
public class PreguntaCasosUs {
    private PreguntaManager preguntaManager;

    public PreguntaCasosUs(PreguntaManager preguntaManager) {
        this.preguntaManager = preguntaManager;
    }

    /***
     *  Afegir pregunta a l'enquesta
     * @param enquesta
     * @param enunciat
     * @param min
     * @param max
     * @return Pregunta creada
     */
    public Pregunta crearPreguntaNumerica(Enquesta enquesta, String enunciat, int min, int max) {
        Pregunta pregunta = new PreguntaNumerica(enquesta,enunciat, min, max);
        return pregunta;
    }

    /***
     *
     * @param pregunta
     * @throws Exception
     */
    public void eliminarPregunta(Pregunta pregunta) throws Exception {
        String msg = String.format("Pregunta eliminada {id:%s, enunciat:\"%s\"} %n", pregunta.getId(), pregunta.getEnunciat());
        preguntaManager.eliminar(pregunta);
        System.out.print(msg);
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
}
