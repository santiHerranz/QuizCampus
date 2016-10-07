package com.tecnocampus.useCases;

import com.tecnocampus.domain.Resposta;
import com.tecnocampus.managers.RespostaManager;

import java.util.List;

/**
 * Created by santi on 7/10/16.
 */
public class RespostaCasosUs {
    private RespostaManager respostaManager;

    public RespostaCasosUs(RespostaManager respostaManager) {
        this.respostaManager = respostaManager;
    }


    public void respondreResposta(List<Resposta> respostes) throws Exception {

        respostes.forEach(r -> respostaManager.crear(r));
        ;
    }
    
}
