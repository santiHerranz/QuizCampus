package com.tecnocampus.useCases;

import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.RespostaNumerica;
import com.tecnocampus.managers.RespostaManager;
import org.springframework.stereotype.Component;

/**
 * Created by santi on 7/10/16.
 */

@Component
public class RespostaCasosUs {
    private RespostaManager respostaManager;

    public RespostaCasosUs(RespostaManager respostaManager) {
        this.respostaManager = respostaManager;
    }

    public Iterable<Resposta> llistarRespostes() throws Exception {
        throw new Exception("No implementat");
    }

    public Iterable<RespostaNumerica> llistarRespostesNumeriques() {
        return respostaManager.llistarRespostesNumeriques();
    }
}
