package com.tecnocampus.security;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Usuari;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by roure on 27/10/2016.
 */
@Service
public class myUserDetailsService implements UserDetailsService {

    private final BeansManager beansManager;

    public myUserDetailsService(BeansManager beansManager) {
        this.beansManager = beansManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuari usuari = null;

        try {
            usuari= beansManager.usuariRepository.findOne(username);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        Set<GrantedAuthority> grantedAuthorities = usuari.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(usuari.getUsername(), usuari.getPassword(), grantedAuthorities);
    }
}
