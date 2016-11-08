package com.tecnocampus.domain;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by santi on 03/10/2016.
 */

public class Usuari extends Clau {

    private String email;
    private String password;
    private String username;
    private List<String> roles;


    private List<Resposta> respostes;

    public Usuari() {
    }

    public Usuari(String email, String username, String contrasenya) {
        if (username==null) throw new NullPointerException("username");
        if (email==null) throw new NullPointerException("email");
        if (contrasenya==null) throw new NullPointerException("password");

        setUsername(username);
        setEmail(email);
        setPassword(contrasenya);
        roles = new ArrayList<>();
        respostes = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addRoles(List<String> roles) {
        this.roles.addAll(roles);
    }

    public List<String> getRoles() {
        return roles;
    }


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
                +", admin: \""+ this.roles +"\""
                //+", respostes("+ this.respostes.size() +") "
                +"}";
    }


    public boolean afegirResposta(Resposta resposta) {
        return this.respostes.add(resposta);
    }

    public void setAdmin(boolean admin) {
        if (this.roles.indexOf("ROLE_ADMIN") == -1)
            this.roles.add("ROLE_ADMIN");
    }

    public boolean isAdmin() {
        return this.roles.indexOf("ROLE_ADMIN") > -1;
    }
}
