package com.tecnocampus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class Enquesta implements Serializable {

    private Long Id;
    private Date dataCreacio;
    private String titol;
    private List<Pregunta> preguntes;

    // Constructor
    public Enquesta() {
    }
    public Enquesta(String titol) {
        this.titol = titol;
        this.preguntes = new ArrayList<>();
    }

    public int respostesRealitzades() {
        if (this.preguntes.size()>0) {
            return this.preguntes.get(0).getRespostes().size();
        }
        return 0;
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

    public String getTitol() {
        return this.titol;
    }
    public void setTitol(String titol) {
        this.titol = titol;
    }

    public List<Pregunta> getPreguntes() {
        return this.preguntes;
    }
    public void setPreguntes(List<Pregunta> preguntes) {
        this.preguntes = preguntes;
    }

    public boolean afegirPregunta(Pregunta p1) {
        return this.preguntes.add(p1);
    }


    public List<RespostaNumerica> createRespostes( ) {
        List<RespostaNumerica> respostes = new ArrayList<RespostaNumerica>();
        for (Pregunta p : this.preguntes ) {
            RespostaNumerica r = new RespostaNumerica();
            r.setPregunta(p);
            respostes.add(r);
        }
        return respostes;
    }


    public List<Resposta> getValoracions() {
        List<Resposta> respostes = new ArrayList<Resposta>();
        for (Pregunta p : this.preguntes ) {
            respostes.addAll(p.getRespostes()) ;
        }
        return respostes;
    }

    public float getValoracioMitjana() {
        float acum = 0.0f, count = 0.0f;
        for (Pregunta p : this.preguntes ) {
            count ++;
            acum += ((PreguntaNumerica)p).getValoracioMitjana();
        }
        if ( count>0.0f)
            return acum/count;
        else
            return 0.0f;
    }


    @Override
    public java.lang.String toString() {
        return String.format("Enquesta[Id: %s, Creat:%s, titol:%s]",
                this.Id, this.dataCreacio, titol);
    }

}
