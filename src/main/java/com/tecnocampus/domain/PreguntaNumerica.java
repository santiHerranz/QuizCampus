package com.tecnocampus.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public final class PreguntaNumerica  extends Pregunta implements Serializable, Comparable {

    public final static int MINIM_ESTRELLES = 1;
    public final static int MAXIM_ESTRELLES = 5;

    private int minim;
    private int maxim;

    public PreguntaNumerica() {
        super();
        this.minim = MINIM_ESTRELLES;
        this.maxim = MAXIM_ESTRELLES;
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



    public float getValoracioMitjana() {
        float acum = 0.0f, count = 0.0f;
        for (Resposta r : this.respostes ) {
            count ++;
            acum += ((RespostaNumerica)r).getValor();
        }
        if ( count>0.0f)
            return acum/count;
        else
            return 0.0f;
    }

    @Override
    public java.lang.String toString() {
        return "{"
                + super.toString()
                +", minim: \""+ this.minim +"\""
                +", maxim: \""+ this.maxim +"\""
                +"}";
    }

    public int compareTo(Pregunta other){
        return this.getOrdre() - other.getOrdre();
    }

    @Override
    public int compareTo(Object o) {
        return this.getOrdre() - ((PreguntaNumerica)o).getOrdre();
    }
}