package com.tecnocampus.useCases;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.managers.EnquestaManager;

/**
 * Created by santi on 6/10/16.
 */
public class EnquestaCasosUs {
    private EnquestaManager enquestaManager;

    public EnquestaCasosUs(EnquestaManager enquestaManager) {
        this.enquestaManager = enquestaManager;
    }

    public Enquesta crearEnquesta(String titol) {
        Enquesta enquesta = new Enquesta(titol);

        return enquesta;
    }


}
