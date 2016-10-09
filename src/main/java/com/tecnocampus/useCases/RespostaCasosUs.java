package com.tecnocampus.useCases;

import com.tecnocampus.databaseRepositories.PreguntaRepository;
import com.tecnocampus.databaseRepositories.RespostaRepository;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.RespostaNumerica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by santi on 7/10/16.
 */

@Service
public class RespostaCasosUs {

    @Autowired
    PreguntaRepository preguntaRespository;

    private RespostaRepository respostaRepository;

    public RespostaCasosUs(RespostaRepository respostaRepository) {

        this.respostaRepository = respostaRepository;
    }

    public List<Resposta> llistarRespostes() {
        return respostaRepository.findAll();
    }
}
