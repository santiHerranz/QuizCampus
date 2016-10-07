package com.tecnocampus.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public abstract class Pregunta extends Clau {

    String enunciat = "";
    Enquesta enquesta;
    List<Resposta> respostes;


    public Pregunta(Enquesta enquesta,String enunciat) {

        setEnquesta(enquesta);
        setEnunciat(enunciat);

        enquesta.getPreguntes().add(this);
        this.respostes = new ArrayList<Resposta>();
    }

    public String getEnunciat() {
        return enunciat;
    }
    public void setEnunciat(String enunciat) {
        this.enunciat = enunciat;
    }

    public Enquesta getEnquesta() {
        return enquesta;
    }
    public void setEnquesta(Enquesta enquesta) {
        this.enquesta = enquesta;
    }

    public List<Resposta> getRespostes() {
        return respostes;
    }
    public void setRespostes(List<Resposta> respostes) {
        this.respostes = respostes;
    }

    public java.lang.String toString() {
        return super.toString() + " titol: '" + this.enunciat + "', preguntes: '" + this.respostes.toString();
    }
}
