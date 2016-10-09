package com.tecnocampus.domain;

/**
 * Created by santi on 03/10/2016.
 *
 * La part comú a totes les respostes
 *  - Qui ha respost
 *  - Quina pregunta s'ha respost
 */
public abstract class Resposta extends Clau implements IResposta {

    private Long usuariId;
    private Usuari usuari;
    private Long preguntaId;
    private Pregunta pregunta;

    public Resposta() {
    }

    public Resposta(Pregunta pregunta, Usuari usuari){
        if (pregunta==null) throw new NullPointerException("pregunta");
        if (usuari==null) throw new NullPointerException("usuari");

        this.setUsuari(usuari);
        this.setPregunta(pregunta);
    }

    public Long getUsuariId() {
        return usuariId;
    }
    public void setUsuariId(long usuariId) {
        this.usuariId = usuariId;
    }
    public Usuari getUsuari() {
        return usuari;
    }
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }


    public Long getPreguntaId() {
        return preguntaId;
    }
    public void setPreguntaId(long preguntaId) {
        this.preguntaId = preguntaId;
    }
    public Pregunta getPregunta() {
        return pregunta;
    }
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }


    public java.lang.String toString() {
        return ""+ super.toString()
                +" "+ this.usuari +""
                +", "+ this.pregunta +""
                +"";
    }

}
