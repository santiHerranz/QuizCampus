package com.tecnocampus.useCases;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.PreguntaNumerica;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.managers.EnquestaRepository;
import com.tecnocampus.managers.PreguntaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by santi on 6/10/16.
 *
 */

@Service
public class EnquestaCasosUs {
    private EnquestaRepository enquestaRepository;
    private PreguntaRepository preguntaRepository;

    public EnquestaCasosUs(EnquestaRepository enquestaRepository, PreguntaRepository preguntaRepository) {
        this.enquestaRepository = enquestaRepository;
        this.preguntaRepository = preguntaRepository;
    }

    public Enquesta crearEnquesta(String titol) {
        Enquesta enquesta = new Enquesta(titol);
        try {
            enquestaRepository.save(enquesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enquesta;
    }

    //The @Transactiona annotation states that saveUser is a transaction. So ,if a unchecked exception is signaled
    // (and not cached) during the saveUser method the transaction is going to rollback
    @Transactional
    public void saveUser(Enquesta user) {
        enquestaRepository.save(user);
    }

    public Pregunta afegirPregunta(Enquesta enquesta, String enunciat, int minim, int maxim) {
        PreguntaNumerica pregunta = new PreguntaNumerica();
        pregunta.setEnunciat(enunciat);
        pregunta.setMinim(minim);
        pregunta.setMaxim(maxim);
        enquesta.afegirPregunta(pregunta);
        preguntaRepository.save(enquesta, pregunta);
        return pregunta;
    }

    public List<Enquesta> llistarEnquestes() {
        return enquestaRepository.findAll();
    }

    public List<Enquesta> llistarUltimesEnquestes() {
        return enquestaRepository.findAllFromUser(null);
    }

    public List<Enquesta> llistarEnquestesDelUsuari(Usuari usuari) {
        return enquestaRepository.findAllFromUser(usuari);
    }

    /***
     * Aquesta funció retorna un booleà en funció de si l'enquesta existeix
     * @param titol
     * @return
     */
    private boolean existeix(String titol) {
        return false;
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
