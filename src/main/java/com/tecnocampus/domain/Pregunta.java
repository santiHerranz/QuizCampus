package com.tecnocampus.domain;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public abstract class Pregunta extends Clau {

    String enunciat = "";

    public Pregunta(String enunciat) {
        setEnunciat(enunciat);
    }

    public String getEnunciat() {
        return enunciat;
    }
    public void setEnunciat(String enunciat) {
        this.enunciat = enunciat;
    }


}
