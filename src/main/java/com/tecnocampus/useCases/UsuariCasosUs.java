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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by santi on 04/10/2016.
 */
@Service
public final class UsuariCasosUs {

    @Autowired
    BeansManager beansManager;
    private Matcher matcher;
    private Pattern pattern;
    private Matcher matcher2;
    private Pattern pattern2;
    private Matcher matcher3;
    private Pattern pattern3;
    private Matcher matcher4;
    private Pattern pattern4;
    private Matcher matcher5;
    private Pattern pattern5;
    private final String patroLlargada =".{9,}";
    private final String patroNumero = "(?=.*[0-9]).{9,}";
    private final String patroLletresMajuscules = "(?=.*[0-9])(?=.*[A-Z]).{9,}";
    private final String patroLletresMinuscules = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{9,}";
    private final String patroNoEspais = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$).{9,}";
    /*
    Explanations:

    (?=.*[0-9]) a digit must occur at least once
    (?=.*[a-z]) a lower case letter must occur at least once
    (?=.*[A-Z]) an upper case letter must occur at least once
    (?=\\S+$) no whitespace allowed in the entire string
    .{8,} at least 8 characters
     */


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

        String resultat = seguretatContrasenya(contrasenya);
        if (resultat != null) throw new ContrasenyaNoValidaException(resultat);

        Usuari usuari = new Usuari(email, contrasenya);
        save(usuari);

        // Establir admin al primer usuari
        if( llistarUsuaris().size()==1)
            promocionarAdmin(usuari);

        System.out.println(usuari);

        return usuari;
    }

    private String seguretatContrasenya(String contrasenya) {
        String resultat = null;
        pattern = Pattern.compile(patroLlargada);
        matcher = pattern.matcher(contrasenya);
        if (!matcher.matches())
            resultat += "La contrasenya no és prou llarga. Ha de tenir un mínim de 9 caràcters. ";
        pattern2 = Pattern.compile(patroNumero);
        matcher2 = pattern2.matcher(contrasenya);
        if (!matcher2.matches()) {
            if (resultat != null) resultat += "No hi ha cap xifra. ";
            else resultat = "No hi ha cap xifra. ";
        }
        pattern3 = Pattern.compile(patroLletresMajuscules);
        matcher3 = pattern3.matcher(contrasenya);
        if (!matcher3.matches()) {
            if (resultat != null) resultat += "No hi ha lletra majúscula. ";
            else resultat = "No hi ha lletra majúscula. ";
        }
        pattern4 = Pattern.compile(patroLletresMinuscules);
        matcher4 = pattern4.matcher(contrasenya);
        if (!matcher4.matches()) {
            if (resultat != null) resultat += "No hi ha lletra minúscula. ";
            else resultat = "No hi ha lletra minúscula. ";
        }
        pattern5 = Pattern.compile(patroNoEspais);
        matcher5 = pattern5.matcher(contrasenya);
        if (!matcher5.matches()) {
            if (resultat != null) resultat += "La contrasenya té espais en blanc.";
            else resultat = "La contrasenya té espais en blanc.";
        }
        return resultat;
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
