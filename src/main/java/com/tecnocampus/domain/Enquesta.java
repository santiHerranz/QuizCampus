package com.tecnocampus.domain;

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
    }

    public String getTitol() {
        return this.titol;
    }
    public void setTitol(String titol) {
        this.titol = titol;
    }

    public List<Pregunta> getPreguntes() {
        return preguntes;
    }
    public void setPreguntes(List<Pregunta> preguntes) {
        this.preguntes = preguntes;
    }


}
