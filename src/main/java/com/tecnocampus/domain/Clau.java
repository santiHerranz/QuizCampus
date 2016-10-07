package com.tecnocampus.domain;

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


    public java.lang.String toString() {
        return "id: '" + this.Id;
    }

}
