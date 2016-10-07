package com.tecnocampus.domain;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public final class PreguntaNumerica  extends Pregunta {

    private String enunciat;
    private int minim;
    private int maxim;

    public PreguntaNumerica(Enquesta enquesta, String enunciat){
        super( enquesta,  enunciat);
    }

    public PreguntaNumerica(Enquesta enquesta, String enunciat, int minim, int maxim){
        super(enquesta, enunciat);
        this.minim = minim;
        this.maxim = maxim;
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

}