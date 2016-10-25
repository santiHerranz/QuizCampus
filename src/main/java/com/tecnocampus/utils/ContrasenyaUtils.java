package com.tecnocampus.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by santi on 19/10/16.
 */
public class ContrasenyaUtils {

    private final Pattern patroLlargada = Pattern.compile(".{9,}");
    private final Pattern patroNumero = Pattern.compile("\\d");
    private final Pattern patroLletresMajuscules = Pattern.compile("(?=.*[A-Z])");
    private final Pattern patroLletresMinuscules = Pattern.compile("(?=.*[a-z])");
    private final Pattern patroNoEspais = Pattern.compile("\\S+$");
    /*
    Explanations:

    (?=.*[0-9]) a digit must occur at least once
    (?=.*[a-z]) a lower case letter must occur at least once
    (?=.*[A-Z]) an upper case letter must occur at least once
    (?=\\S+$) no whitespace allowed in the entire string
    .{8,} at least 8 characters
     */

    private ArrayList<String> errorList;

    public ContrasenyaUtils (){
        errorList = new ArrayList<String>();
    }

    public ArrayList<String> getErrors() {
        return errorList;
    }


    public boolean esValida(String contrasenya) {

        Matcher matcher = patroLlargada.matcher(contrasenya);
        if (!matcher.matches())
            errorList.add("La contrasenya no és prou llarga. Ha de tenir un mínim de 9 caràcters.");


        if (!patroNumero.matcher(contrasenya).find()) {
            errorList.add("No hi ha cap xifra. ");
        }

        if (!patroLletresMajuscules.matcher(contrasenya).find()) {
            errorList.add("No hi ha lletra majúscula. ");
        }

        if (!patroLletresMinuscules.matcher(contrasenya).find()) {
            errorList.add("No hi ha lletra minúscula.");
        }

        matcher = patroNoEspais.matcher(contrasenya);
        if (!matcher.matches()) {
            errorList.add("La contrasenya té espais en blanc.");
        }

        return errorList.size()==0;
    }


}
