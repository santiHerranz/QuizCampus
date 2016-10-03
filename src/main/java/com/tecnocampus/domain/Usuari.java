package com.tecnocampus.domain;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by santi on 03/10/2016.
 */

public class Usuari {

    private String email;
    private String contrasenya;
    private boolean admin = false;

    private List<Resposta> respostes;

    public Usuari() {
        respostes = new ArrayList<>();
    }

    public Usuari(String email, String contrasenya) {
        this();
        setEmail(email);
        setContrasenya(contrasenya);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Resposta> getRespostes() {
        return respostes;
    }

    public void setRespostes(List<Resposta> respostes) {
        this.respostes = respostes;
    }


}
