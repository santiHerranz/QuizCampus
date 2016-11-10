package com.tecnocampus.domain;

import java.util.Date;

/**
 * Created by santi on 03/10/2016.
 *
 * La part comú a totes les respostes
 *  - Qui ha respost
 *  - Quina pregunta s'ha respost
 */
public abstract class Resposta implements IResposta {

    private Long Id;
    private Date dataCreacio;
    private Usuari usuari;
    private Pregunta pregunta;

    public Resposta() {
    }

    public Resposta(Pregunta pregunta, Usuari usuari){
        if (pregunta==null) throw new NullPointerException("pregunta");
        if (usuari==null) throw new NullPointerException("usuari");

        this.setUsuari(usuari);
        this.setPregunta(pregunta);
    }

    public Long getId() {
        return Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }
    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }


    public Usuari getUsuari() {
        return usuari;
    }
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }


    public String toString() {
        return String.format("Resposta[Id: %s, Creat:%s, usuari:%s, %s]",
                Id, dataCreacio, usuari, usuari, pregunta);
    }


}
