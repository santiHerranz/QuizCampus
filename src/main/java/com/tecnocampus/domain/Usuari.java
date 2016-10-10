package com.tecnocampus.domain;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by santi on 03/10/2016.
 */

public class Usuari extends Clau {

    private String email;
    private String contrasenya;
    private boolean admin;

    private List<Resposta> respostes;

    public Usuari(String email, String contrasenya) {
        if (email==null) throw new NullPointerException("email");
        if (contrasenya==null) throw new NullPointerException("contrasenya");

        setEmail(email);
        setContrasenya(contrasenya);
        respostes = new ArrayList<>();
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

    public void setAdmin(boolean admin) { this.admin = admin;  }


    public List<Resposta> getRespostes() {
        return respostes;
    }

    public void setRespostes(List<Resposta> respostes) {
        this.respostes = respostes;
    }


    public java.lang.String toString() {

        return "{"
                + super.toString()
                +" email: \""+ this.email +"\""
                +" admin: \""+ this.admin +"\""
                //+", respostes("+ this.respostes.size() +") "
                +"}";
    }


    public boolean afegirResposta(Resposta resposta) {
        return this.respostes.add(resposta);
    }
}
