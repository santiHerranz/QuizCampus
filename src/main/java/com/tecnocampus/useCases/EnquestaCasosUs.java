package com.tecnocampus.useCases;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.managers.EnquestaManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by santi on 6/10/16.
 *
 */

@Component
public final class EnquestaCasosUs {
    private EnquestaManager enquestaManager;

    public EnquestaCasosUs(EnquestaManager enquestaManager) {
        this.enquestaManager = enquestaManager;
    }

    public Enquesta crearEnquesta(String titol) {
        Enquesta enquesta = new Enquesta(titol);

        try {
            enquestaManager.crear(enquesta);
            System.out.format("Nova enquesta {id:%s, titol:\"%s\"} %n", enquesta.getId(), enquesta.getTitol());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enquesta;
    }

    public List<Enquesta> llistarEnquestes() {
        return enquestaManager.llistarEnquestes(null);
    }

    public List<Enquesta> llistarUltimesEnquestes() {
        return enquestaManager.llistarEnquestes(null);
    }

    public List<Enquesta> llistarEnquestesperUsuari(Usuari usuari) {
        return enquestaManager.llistarEnquestes(usuari);
    }


    /***
     *
     * @param enquesta
     * @throws Exception
     */
    public void eliminarEnquesta(Enquesta enquesta) throws Exception {
        throw new Exception();
    }

}
