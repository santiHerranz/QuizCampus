package com.tecnocampus.useCases;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.*;
import com.tecnocampus.exceptions.EnquestaDuplicadaException;
import com.tecnocampus.exceptions.PreguntaNoExisteixException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by santi on 6/10/16.
 *
 */

@Service("enquestaCasosUs")
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

        //obtenir el valor d'ordre més gran
        int max = beansManager.preguntaRepository.findMaxOrder(enquesta);
        pregunta.setOrdre(max+1);

        enquesta.afegirPregunta(pregunta);
        beansManager.preguntaRepository.save(enquesta, pregunta);
        return pregunta;
    }


    @Transactional
    public Resposta afegirResposta(Pregunta pregunta, Usuari u,  int valor) {

        RespostaNumerica resposta = new RespostaNumerica();
        resposta.setUsuari(u);
        resposta.setValor(valor);

        beansManager.respostaRepository.save(pregunta, resposta);
        return resposta;
    }

    public void reordenarPreguntes(Enquesta enquesta, String orderedKeyList) {

        List<String> keys = Arrays.asList(orderedKeyList.split(","));
        for (Pregunta p : enquesta.getPreguntes()) {
            Long Id = p.getId();
            int posicio = keys.indexOf(Id.toString());
            if ( posicio != -1)
                p.setOrdre(posicio+1);
            beansManager.preguntaRepository.save(enquesta, p);
        }
    }




    public void afegirPreguntes(Enquesta enquesta, BossaPreguntes bossaPreguntes) { /* */

        bossaPreguntes.getPreguntes().forEach(preguntaConsumer -> {
                PreguntaNumerica p = (PreguntaNumerica)preguntaConsumer;
                afegirPregunta(enquesta,p.getEnunciat(), p.getMinim(), p.getMaxim());
        });
    }

    public void afegirRespostes(Enquesta enquesta, BossaRespostes bossaRespostes) throws Exception { /* */
        if ( bossaRespostes == null )
            throw new IllegalArgumentException("La bossa de respostes es null");
        if ( bossaRespostes.getRespostes().size() == 0 )
            throw new IllegalArgumentException("La bossa de respostes esta buida");

        //Usuari u = beansManager.usuariRepository.findOne(1L);
        Usuari u = beansManager.usuariRepository.findOne( getPrincipal());


        bossaRespostes.getRespostes().forEach(respostaConsumer -> {
            RespostaNumerica r = (RespostaNumerica)respostaConsumer;
            afegirResposta(r.getPregunta(), u, r.getValor());
        });
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


    public Enquesta obtenirEnquesta(long enquestaId) {
        System.out.println("obtenirEnquesta");

        return beansManager.enquestaRepository.findOne(enquestaId);
    }


    public Pregunta obtenirPregunta(long preguntaId) {
        return beansManager.preguntaRepository.findOne(preguntaId);
    }


    /***
     * Després d'eliminar la pregunta s'ha d'actualitzar l'ordre de les preguntes que queden
     * @param pregunta
     * @return
     * @throws Exception
     */
    public void eliminarPregunta(Pregunta pregunta) throws RuntimeException {
        if (beansManager.preguntaRepository.findOne(pregunta.getId()) == null)
            throw new PreguntaNoExisteixException();

        Enquesta enquesta = obtenirEnquesta(pregunta.getEnquesta().getId());
        String s = "";
        for (Pregunta p: enquesta.getPreguntes()) {
            if(p.getId() != pregunta.getId()) {
                if(s != "") s += ",";
                s += p.getId();
            }
        }
        beansManager.preguntaRepository.delete(pregunta);

        reordenarPreguntes(enquesta,s);
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
