package com.tecnocampus.domain;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class Enquesta extends Clau {

    private String titol;

    public Enquesta(String titol) {
        this.titol = titol;
    }

    public String getTitol() {
        return this.titol;
    }
    public void setTitol(String titol) {
        this.titol = titol;
    }

}
