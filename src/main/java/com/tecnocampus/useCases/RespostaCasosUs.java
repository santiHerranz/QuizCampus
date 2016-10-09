package com.tecnocampus.useCases;

import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.RespostaNumerica;
import com.tecnocampus.databaseRepositories.RespostaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by santi on 7/10/16.
 */

@Service
public class RespostaCasosUs {
    private RespostaRepository respostaRepository;

    public RespostaCasosUs(RespostaRepository respostaRepository) {
        this.respostaRepository = respostaRepository;
    }

    public Iterable<Resposta> llistarRespostes() throws Exception {
        throw new Exception("No implementat");
    }

    public Iterable<RespostaNumerica> llistarRespostesNumeriques() {
        return respostaRepository.llistarRespostesNumeriques();
    }
}
