package com.tecnocampus.useCases;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.managers.UsuariManager;
import org.springframework.stereotype.Component;

/**
 * Created by santi on 04/10/2016.
 */
@Component
public class UserUseCases  {
    private UsuariManager userLabRepository;

    public UserUseCases(UsuariManager userLabRepository) {
        this.userLabRepository = userLabRepository;
    }

    public Usuari createUser(String email, String contrasenya) {
        Usuari usuari = new Usuari(email, contrasenya);
        try {
            userLabRepository.crear(usuari);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuari;
    }

}
