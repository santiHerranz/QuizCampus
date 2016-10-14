package com.tecnocampus.useCases;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Resposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by santi on 7/10/16.
 */

@Service
public class RespostaCasosUs {

    @Autowired
    BeansManager beansManager;

    public RespostaCasosUs() {
    }

    public List<Resposta> llistarRespostes() {
        return beansManager.respostaRepository.findAll();
    }

    public void esborraResposta(Resposta r) {
        beansManager.respostaRepository.delete(r);
    }

    public Resposta obtenirResposta(Long respostaId) {
        return beansManager.respostaRepository.findOne(respostaId);
    }
}
