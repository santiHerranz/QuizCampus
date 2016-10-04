package com.tecnocampus.domain;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class Enquesta {

    private int enquestaID;
    private String titol;

    public Enquesta(int enquestaID, String titol) {
        this.enquestaID = enquestaID;
        this.titol = titol;
    }

    public int getEnquestaID() {
        return this.enquestaID;
    }

    public String getTitol() {
        return this.titol;
    }

    public void setEnquestaID(int enquestaID) {
        this.enquestaID = enquestaID;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

}
