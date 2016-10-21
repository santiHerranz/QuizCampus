package com.tecnocampus.domain;

import java.util.Date;

/**
 * Created by santi on 05/10/2016.
 */

public abstract class Clau {

    private Long Id;

    public Long getId() {
        return Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }

    private Date dataCreacio;
    public Date getDataCreacio() {
        return dataCreacio;
    }
    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }


    public java.lang.String toString() {
        return String.format("Id: %s, Creat:%s,",this.Id, this.dataCreacio);
    }

}
