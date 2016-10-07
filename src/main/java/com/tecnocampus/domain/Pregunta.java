package com.tecnocampus.domain;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public abstract class Pregunta extends Clau {

    String enunciat = "";
    Enquesta enquesta;


    public Pregunta(Enquesta enquesta,String enunciat) {
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

}
