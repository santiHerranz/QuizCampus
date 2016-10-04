package com.tecnocampus.managers;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Usuari;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class EnquestaManager {

    /***
     * Mètode public per a crear noves enquestes
     * @param titol
     * @return
     */
    public Enquesta crearEnquesta(String titol) {
        return null;
    }

    /***
     * Aquesta funció llista totes les enquestes que hagi respost l'usuari que li passem per parametre
     * @param usuari
     */
    public void llistarEnquestes(Usuari usuari) {
    }

    /***
     * Aquesta funció llista totes les preguntes que pertanyin a l'esquesta que passem per parametre
     * @param enquesta
     */
    public void llistarPreguntes(Enquesta enquesta) {

    }

    /***
     * Aquesta funció retorna l'enquesta amb el títol que li passem per paràmetre
     * @param titol
     * @return Enquesta o null
     */
    public Enquesta obtenirEnquesta(String titol) {
        return null;
    }

    /***
     * Aquesta funció retorna l'enquesta amb l'identificador que li passem per paràmetre
     * @param enquestaID
     * @return
     */
    public Enquesta obtenirEnquesta(int enquestaID) {
        return null;
    }

    /***
     * Aquesta funció retorna un booleà en funció de si l'enquesta existeix
     * @param titol
     * @return
     */
    private boolean existeix(String titol) {
        return false;
    }
}
