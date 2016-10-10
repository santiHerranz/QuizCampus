package com.tecnocampus.domain;

/**
 * Created by santi on 09/10/2016.
 */
public interface IPregunta {

    public boolean afegirResposta(Resposta resposta);
    public boolean afegirResposta(Usuari usuari, int valor);

}
