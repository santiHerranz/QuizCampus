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

    @Override
    public java.lang.String toString() {
        return String.format("Enquesta[Id: %s, Creat:%s, titol:%s]",
                this.Id, this.dataCreacio, titol);
    }

}
