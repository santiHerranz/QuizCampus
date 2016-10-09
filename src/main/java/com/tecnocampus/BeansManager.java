package com.tecnocampus;

import com.tecnocampus.databaseRepositories.EnquestaRepository;
import com.tecnocampus.databaseRepositories.PreguntaRepository;
import com.tecnocampus.databaseRepositories.RespostaRepository;
import com.tecnocampus.databaseRepositories.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by santi on 09/10/2016.
 */
@Component
public class BeansManager {

    @Autowired
     public RespostaRepository respostaRepository;

    @Autowired
    public PreguntaRepository preguntaRepository;

    @Autowired
    public EnquestaRepository enquestaRepository;

    @Autowired
    public UsuariRepository usuariRepository;
}
