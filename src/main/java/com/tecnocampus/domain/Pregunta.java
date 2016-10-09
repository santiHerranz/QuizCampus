package com.tecnocampus.domain;

import java.util.List;


/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public abstract class Pregunta extends Clau implements IPregunta {

    private String enunciat = "";
    private Long enquestaId;
    private Enquesta enquesta;
    protected List<Resposta> respostes;

    public Pregunta(){
    }

    public Pregunta(Enquesta enquesta, String enunciat) {
        this();
        setEnquesta(enquesta);
        setEnunciat(enunciat);
    }

    public Long getEnquestaId() {
        return enquestaId;
    }

    public void setEnquestaId(Long enquestaId) {
        this.enquestaId = enquestaId;
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
        return ""+ super.toString()
                +" enunciat: \""+ this.enunciat +"\""
                +", "+ this.enquesta +""
                +"";
    }


}
