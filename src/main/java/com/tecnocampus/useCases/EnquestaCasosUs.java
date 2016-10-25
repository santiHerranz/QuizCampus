package com.tecnocampus.useCases;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.PreguntaNumerica;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.EnquestaDuplicadaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by santi on 6/10/16.
 *
 */

@Service
public class EnquestaCasosUs {

    @Autowired
    BeansManager beansManager;

    public EnquestaCasosUs() {
    }

    //The @Transactiona annotation states that save is a transaction. So ,if a unchecked exception is signaled
    // (and not cached) during the save method the transaction is going to rollback

    @Transactional
    public Enquesta crearEnquesta(String titol) {
        Enquesta enquesta = new Enquesta(titol);
        enquesta = save(enquesta);
        return enquesta;
    }

    @Transactional
    public Enquesta save(Enquesta enquesta) {
        try {
            return beansManager.enquestaRepository.save(enquesta);
        } catch (DuplicateKeyException e) {
            throw new EnquestaDuplicadaException();
        }
    }


    @Transactional
    public void eliminarEnquesta(Enquesta enquesta) {
        beansManager.enquestaRepository.eliminarEnquesta(enquesta);
    }

    @Transactional
    public Pregunta afegirPregunta(Enquesta enquesta, String enunciat, int minim, int maxim) {
        PreguntaNumerica pregunta = new PreguntaNumerica();
        pregunta.setEnunciat(enunciat);
        pregunta.setMinim(minim);
        pregunta.setMaxim(maxim);

        enquesta.afegirPregunta(pregunta);
        beansManager.preguntaRepository.save(enquesta, pregunta);
        return pregunta;
    }

    public List<Enquesta> llistarEnquestes() {
        return beansManager.enquestaRepository.findAll();
    }

    public List<Enquesta> llistarEnquestesDelUsuari(Usuari usuari) {
        return beansManager.enquestaRepository.findAllFromUser(usuari);
    }

    /***
     * Aquesta funció retorna un booleà en funció de si l'enquesta existeix
     * @param titol
     * @return booleà: true o false
     */
    private boolean existeix(String titol) {
        if (beansManager.enquestaRepository.findOne(titol) != null) return true;
        else return false;
    }


    public Enquesta obetenirEnquesta(long enquestaId) {
        return beansManager.enquestaRepository.findOne(enquestaId);
    }
}
