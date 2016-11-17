package com.tecnocampus.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by santi on 03/10/2016.
 */

public class Usuari implements Serializable {

    private Long Id;
    private Date dataCreacio;
    private String password;
    private String username;
    private List<String> roles;


    private List<Resposta> respostes;

    public Usuari() {
    }

    public Usuari(String username, String contrasenya) {
        if (contrasenya==null) throw new NullPointerException("password");

        setUsername(username);
        setPassword(contrasenya);
        roles = new ArrayList<>();
        roles.add("ROLE_USER");
        respostes = new ArrayList<>();
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


    @Override
    public java.lang.String toString() {
        return String.format("Enquesta[Id: %s, Creat:%s, roles:%s]",
                this.Id, this.dataCreacio, roles);
    }


    public boolean afegirResposta(Resposta resposta) {
        return this.respostes.add(resposta);
    }

    public void setAdmin(boolean admin) {
        if(admin)
            if (this.roles.indexOf("ROLE_ADMIN") == -1)
                this.roles.add("ROLE_ADMIN");
        else {
            int index = this.roles.indexOf("ROLE_ADMIN");
            if ( index > -1)
                this.roles.remove(index);
        }
    }

    public boolean isAdmin() {
        return this.roles.indexOf("ROLE_ADMIN") > -1;
    }
}
