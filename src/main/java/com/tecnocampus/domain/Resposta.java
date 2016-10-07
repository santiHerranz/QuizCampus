package com.tecnocampus.domain;

/**
 * Created by santi on 03/10/2016.
 *
 * La part comú a totes les respostes
 *  - Qui ha respost
 *  - Quina pregunta s'ha respost
 */
public abstract class Resposta extends Clau {

    Usuari usuari;
    Pregunta preguntaId;

    public Resposta(Pregunta pregunta, Usuari usuari){
        this.setPregunta(pregunta);
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Pregunta getPregunta() {
        return preguntaId;
    }

    public void setPregunta(Pregunta preguntaId) {
        this.preguntaId = preguntaId;
    }


}
