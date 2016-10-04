package com.tecnocampus.domain;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class PreguntaNumerica  implements Pregunta {
    private int preguntaID;
    private String enunciat;
    private int minim;
    private int maxim;

    public PreguntaNumerica(int preguntaID, String enunciat, int minim, int maxim){
        this.preguntaID = preguntaID;
        this.enunciat = enunciat;
        this.minim = minim;
        this.maxim = maxim;
    }

    public int getPreguntaID() {
        return preguntaID;
    }

    public void setPreguntaID(int preguntaID) {
        this.preguntaID = preguntaID;
    }

    public String getEnunciat() {
        return enunciat;
    }

    public void setEnunciat(String enunciat) {
        this.enunciat = enunciat;
    }

    public int getMinim() {
        return minim;
    }

    public void setMinim(int minim) {
        this.minim = minim;
    }

    public int getMaxim() {
        return maxim;
    }

    public void setMaxim(int maxim) {
        this.maxim = maxim;
    }

    public void mostraRang() {
        //TODO hem de decidir com mostrarem el rang
    }
}