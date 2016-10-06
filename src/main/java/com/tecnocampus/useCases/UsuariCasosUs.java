package com.tecnocampus.useCases;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.managers.UsuariManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by santi on 04/10/2016.
 */
@Component
public final class UsuariCasosUs {
    private UsuariManager usuariManager;

    public UsuariCasosUs(UsuariManager usuariManager) {
        this.usuariManager = usuariManager;
    }

    public Usuari crearUsuari(String email, String contrasenya) {
        Usuari usuari = new Usuari(email, contrasenya);
        try {
            usuariManager.crear(usuari);
            System.out.format("Nou usuari creat {id:%s, email:\"%s\"} %n", usuari.getId(), usuari.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Establir admin al primer usuari
        if( llistarUsuaris().size()==1)
            ferAdmin(usuari);

        return usuari;
    }

    public boolean comprobarContrasenya(String contrasenya, Usuari usuari) {
        return contrasenya == usuari.getContrasenya();
    }

    public void ferAdmin(Usuari usuari) {
        usuari.setAdmin(true);
        usuariManager.guardar(usuari);
        System.out.format("Usuari promocionat a Admin {id:%s, email:\"%s\"} %n", usuari.getId(), usuari.getEmail());
    }

    public void desferAdmin(Usuari usuari) throws Exception {
        if(esUltimAdmin(usuari))
            throw new Exception(String.format("L'usuari {id:%s, email:\"%s\"} és l'ultim administrador, no es pot degradar!!!", usuari.getId(), usuari.getEmail()));
        usuari.setAdmin(false);
        usuariManager.guardar(usuari);
        System.out.format("Usuari degradat {id:%s, email:\"%s\"} %n", usuari.getId(), usuari.getEmail());
    }

    private boolean esUltimAdmin(Usuari usuari){
        if(!usuari.isAdmin()) return  false;
        List<Usuari> admins = llistarUsuaris().stream().filter(u -> u.isAdmin() == true).collect(Collectors.toList());
        return admins.size()==1;
    }


    /***
     * Elimina l'usuari
     * @param usuari
     * @throws Exception si es vol eliminar l'ultim administrador
     */
    public void eliminarUsuari(Usuari usuari) throws Exception {

        if(esUltimAdmin(usuari))
            throw new Exception(String.format("L'usuari {id:%s, email:\"%s\"} és l'ultim administrador, no es pot eliminar!!!", usuari.getId(), usuari.getEmail()));

        String msg = String.format("Usuari eliminat {id:%s, email:\"%s\"} %n", usuari.getId(), usuari.getEmail());
        usuariManager.eliminar(usuari);
        System.out.print(msg);
    }

    public List<Usuari> llistarUsuaris() {
        return usuariManager.llistar();
    }
}
