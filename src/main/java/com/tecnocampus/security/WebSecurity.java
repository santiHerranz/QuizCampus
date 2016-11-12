package com.tecnocampus.security;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Usuari;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by roure on 31/10/2016.
 */
@Component
public class WebSecurity {

    @Autowired
    BeansManager beansManager;

    public boolean checkUserId(Authentication authentication, String id) {

        Usuari u = beansManager.usuariRepository.findOne(Long.parseLong(id));
        if (u == null) return false;
        return u.getUsername().equals(authentication.getName());
    }


}
