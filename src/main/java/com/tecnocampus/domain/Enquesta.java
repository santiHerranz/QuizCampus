package com.tecnocampus.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class Enquesta extends Clau {

    private String titol;
    private List<Pregunta> preguntes;

    // Constructor
    public Enquesta(String titol) {
        this.titol = titol;
        this.preguntes = new ArrayList<>();
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

    @Override
    public java.lang.String toString() {
        return "Enquesta {"
                + super.toString()
                +" titol: \""+ this.getTitol() +"\""
                +"}";
    }

}
