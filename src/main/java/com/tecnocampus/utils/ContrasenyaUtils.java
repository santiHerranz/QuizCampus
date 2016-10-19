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

        pattern2 = Pattern.compile(patroNumero);
        matcher2 = pattern2.matcher(contrasenya);
        if (!matcher2.matches()) {
            errorList.add("No hi ha cap xifra. ");
        }

        pattern3 = Pattern.compile(patroLletresMajuscules);
        matcher3 = pattern3.matcher(contrasenya);
        if (!matcher3.matches()) {
            errorList.add("No hi ha lletra majúscula. ");
        }

        pattern4 = Pattern.compile(patroLletresMinuscules);
        matcher4 = pattern4.matcher(contrasenya);
        if (!matcher4.matches()) {
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
