package com.tecnocampus.security;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.UsuariNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by roure on 27/10/2016.
 */
@Service
public class myUserDetailsService implements UserDetailsService {


    @Autowired
    private BeansManager beansManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsuariNotFoundException {

        Usuari usuari = beansManager.usuariRepository.findOne(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        usuari.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));

        return new org.springframework.security.core.userdetails.User(usuari.getUsername(), usuari.getPassword(), grantedAuthorities);
    }
}
