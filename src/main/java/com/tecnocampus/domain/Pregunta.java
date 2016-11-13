package com.tecnocampus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public abstract class Pregunta implements IPregunta, Serializable {

    private Long Id;
    private Date dataCreacio;
    private String enunciat = "";
    private Enquesta enquesta;
    protected List<Resposta> respostes;

    public Pregunta(){
        respostes = new ArrayList<Resposta>();
    }

    public Pregunta(Enquesta enquesta, String enunciat) {
        this();
        setEnquesta(enquesta);
        setEnunciat(enunciat);
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


    public String getEnunciat() {
        return enunciat;
    }
    public void setEnunciat(String enunciat) {
        this.enunciat = enunciat;
    }

    public Enquesta getEnquesta() {
        return enquesta;
    }
    public void setEnquesta(Enquesta enquesta) {
        this.enquesta = enquesta;
    }

    public List<Resposta> getRespostes() {
        return respostes;
    }
    public void setRespostes(List<Resposta> respostes) {
        this.respostes = respostes;
    }


    public java.lang.String toString() {
        return String.format("Pregunta[Id: %s, Creat:%s, enunciat:%s, %s]",
                Id, dataCreacio, enunciat, enquesta);
    }


}
