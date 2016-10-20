package com.tecnocampus.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by santi on 19/10/16.
 */
public class ContrasenyaUtils {

    private Matcher matcher;
    private Pattern pattern;
    private Matcher matcher5;
    private Pattern pattern5;
    private final String patroLlargada =".{9,}";
    private final Pattern patroNumero = Pattern.compile("\\d");
    private final Pattern patroLletresMajuscules = Pattern.compile("(?=.*[A-Z])");
    private final Pattern patroLletresMinuscules = Pattern.compile("(?=.*[a-z])");
    private final String patroNoEspais = "\\S+$";
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

        pattern = Pattern.compile(patroLlargada);
        matcher = pattern.matcher(contrasenya);
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

        pattern5 = Pattern.compile(patroNoEspais);
        matcher5 = pattern5.matcher(contrasenya);
        if (!matcher5.matches()) {
            errorList.add("La contrasenya té espais en blanc.");
        }

        return errorList.size()==0;
    }


}
