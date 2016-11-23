package com.tecnocampus.domain;

import java.io.Serializable;

/**
 * Created by santi on 03/10/2016.
 *
 *  Part específica de la resposta numérica
 *  - Valor numéric entre els valors límits de la pregunta
 */
public final class RespostaNumerica extends Resposta implements Serializable, Comparable {

    private int valor;

    public RespostaNumerica() {
        super();
    }
    public RespostaNumerica(Pregunta pregunta, Usuari usuari, int valor) {
        super(pregunta, usuari);
        this.valor = valor;
    }


    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }

    public java.lang.String toString() {
        return "{"
                + super.toString()
                +", valor: \""+ this.valor +"\""
                +"}";
    }

    public int compareTo(RespostaNumerica other){
        return this.getPregunta().getOrdre() - other.getPregunta().getOrdre();
    }

    @Override
    public int compareTo(Object o) {
        return this.getPregunta().getOrdre() - ((RespostaNumerica)o).getPregunta().getOrdre();
    }

}
