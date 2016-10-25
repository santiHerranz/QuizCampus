package com.tecnocampus.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public final class PreguntaNumerica  extends Pregunta implements Serializable {

    private int minim;
    private int maxim;

    public PreguntaNumerica() {
        super();
    }

    public PreguntaNumerica(Enquesta enquesta, String enunciat){
        super(enquesta, enunciat);
    }

    public PreguntaNumerica(Enquesta enquesta, String enunciat, int minim, int maxim){
        super(enquesta, enunciat);
        this.minim = minim;
        this.maxim = maxim;
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


    public boolean afegirResposta(Resposta resposta) {
        if(this.respostes == null)
            this.respostes = new ArrayList<Resposta>();
        return this.getRespostes().add(resposta);
    }

    public boolean afegirResposta(Usuari usuari, int valor) {
        return this.getRespostes().add(new RespostaNumerica(this,usuari, valor));
    }

    @Override
    public java.lang.String toString() {
        return "{"
                + super.toString()
                +", minim: \""+ this.minim +"\""
                +", maxim: \""+ this.maxim +"\""
                +"}";
    }


}