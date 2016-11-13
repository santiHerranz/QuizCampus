package com.tecnocampus.useCases;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.ContrasenyaNoValidaException;
import com.tecnocampus.exceptions.UsuariDuplicatException;
import com.tecnocampus.utils.ContrasenyaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Usuari crearUsuari(String username, String contrasenya) {
        return crearUsuari(username, contrasenya, contrasenya);
    }

    public Usuari crearUsuari(String username, String contrasenya, String confirmaContrasenya) {
         if(contrasenya == null ) throw new IllegalArgumentException();

        if(!confirmaContrasenya.equals(contrasenya))
            throw  new RuntimeException("La contrasenya no coincideix");

        // La contrasenya ha de cumplir les normes especificades
        ContrasenyaUtils contrasenyaUtils = new ContrasenyaUtils();
        if (!contrasenyaUtils.esValida(contrasenya)) {
            ArrayList<String> errorDescription = contrasenyaUtils.getErrors();

            if (errorDescription.size()>0) {
                String errorJoined = String.join("&&", errorDescription);
                throw new ContrasenyaNoValidaException(errorJoined);
            }
        }

        Usuari usuari = new Usuari(username, contrasenya);
        usuari = save(usuari);

        // Establir admin al primer usuari
        if( llistarUsuaris().size()==1)
            promocionarAdmin(usuari);

        System.out.println(usuari);

        return usuari;
    }


    public Usuari save(Usuari usuari) {
        try {
            return beansManager.usuariRepository.save(usuari);
        } catch (DuplicateKeyException e) {
            throw new UsuariDuplicatException();
        }
    }

    public boolean comprobarContrasenya(String username, String contrasenya) {
        if(username == null ) throw new IllegalArgumentException();
        if(contrasenya == null ) throw new IllegalArgumentException();

        Usuari usuari = beansManager.usuariRepository.findOne(username);
        if( usuari == null)
            throw new RuntimeException("Username no trobat!");

        return contrasenya == usuari.getPassword();
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

        String msg = String.format("Usuari eliminat {id:%s, username:\"%s\"} %n", usuari.getId(), usuari.getUsername());
        beansManager.usuariRepository.delete(usuari);
        System.out.print(msg);
    }

    public List<Usuari> llistarUsuaris() {
        return beansManager.usuariRepository.findAll();
    }

    public Usuari cercarUsuari(String username) {
        return beansManager.usuariRepository.findOne(username);
    }

    public Usuari cercarUsuari(long usuariId) {
        return beansManager.usuariRepository.findOne(usuariId);
    }

    public void esborraResposta(Resposta resposta) {
        beansManager.respostaRepository.delete(resposta);
    }

}
