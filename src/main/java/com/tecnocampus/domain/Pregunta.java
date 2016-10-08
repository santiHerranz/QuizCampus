package com.tecnocampus.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public abstract class Pregunta extends Clau {

    private String enunciat = "";
    private Enquesta enquesta;
    private List<Resposta> respostes;

    public Pregunta(){
        this.respostes = new ArrayList<Resposta>();
    }

    public Pregunta(String enunciat) {
        this();

        setEnquesta(enquesta);
        setEnunciat(enunciat);
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


    public abstract boolean afegirResposta(Usuari usuari, int valor);



}
