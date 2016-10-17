package com.tecnocampus.useCases;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.ContrasenyaNoValidaException;
import com.tecnocampus.exceptions.UsuariDuplicatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by santi on 04/10/2016.
 */
@Service
public final class UsuariCasosUs {

    @Autowired
    BeansManager beansManager;


    public UsuariCasosUs() {
    }
    public Usuari crearUsuari(String email, String contrasenya) {
        return crearUsuari(email, contrasenya, contrasenya);
    }

    public Usuari crearUsuari(String email, String contrasenya, String confirmaContrasenya) {
        if(email == null ) throw new IllegalArgumentException();
        if(contrasenya == null ) throw new IllegalArgumentException();

        if(!confirmaContrasenya.equals(contrasenya))
            throw  new RuntimeException("La contrasenya no coincideix");

        if (contrasenya.length() < 3)
            throw new ContrasenyaNoValidaException();

        Usuari usuari = new Usuari(email, contrasenya);
        save(usuari);

        // Establir admin al primer usuari
        if( llistarUsuaris().size()==1)
            promocionarAdmin(usuari);

        System.out.println(usuari);

        return usuari;
    }

    public void save(Usuari usuari) {
        try {
            beansManager.usuariRepository.save(usuari);
        } catch (DuplicateKeyException e) {
            throw new UsuariDuplicatException();
        }
    }

    public boolean comprobarContrasenya(String email, String contrasenya) {
        if(email == null ) throw new IllegalArgumentException();
        if(contrasenya == null ) throw new IllegalArgumentException();

        Usuari usuari = beansManager.usuariRepository.findOne(email);
        if( usuari == null)
            throw new RuntimeException("Email no trobat!");

        return contrasenya == usuari.getContrasenya();
    }

    public void promocionarAdmin(Usuari usuari) {
        usuari.setAdmin(true);
        beansManager.usuariRepository.save(usuari);
    }

    public void degradarAdmin(Usuari usuari){
        if(!usuari.isAdmin())
            throw new RuntimeException("L'usuari no és administrador, no es pot degradar!!!");
        if(esUltimAdmin(usuari))
            throw new RuntimeException("L'usuari és l'ultim administrador, no es pot degradar!!!");
        usuari.setAdmin(false);
        beansManager.usuariRepository.save(usuari);
    }

    private boolean esUltimAdmin(Usuari usuari){
        if(!usuari.isAdmin()) return  false;
        List<Usuari> admins = llistarUsuaris().stream().filter(u -> u.isAdmin() == true).collect(Collectors.toList());
        return admins.size()==1;
    }

    /***
     * Elimina l'usuari
     * @param usuari
     * @throws Exception si es vol delete l'ultim administrador
     */
    public void eliminarUsuari(Usuari usuari) {

        if(esUltimAdmin(usuari))
            throw new RuntimeException("L'usuari és l'ultim administrador, no es pot eliminar!!!");

        String msg = String.format("Usuari eliminat {id:%s, email:\"%s\"} %n", usuari.getId(), usuari.getEmail());
        beansManager.usuariRepository.delete(usuari);
        System.out.print(msg);
    }

    public List<Usuari> llistarUsuaris() {
        return beansManager.usuariRepository.findAllLazy();
    }

    public Usuari cercarUsuari(String email) {
        return beansManager.usuariRepository.findOne(email);
    }

    public Usuari cercarUsuari(long usuariId) {
        return beansManager.usuariRepository.findOne(usuariId);
    }

    public void esborraResposta(Resposta resposta) {
        beansManager.respostaRepository.delete(resposta);
    }
}
