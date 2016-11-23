package com.tecnocampus.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by santi on 25/10/16.
 */
public class BossaRespostes implements Serializable {
        private ArrayList<RespostaNumerica> respostes;

        public BossaRespostes() {
            this.respostes = new ArrayList<>();
        }

        public void addResposta(RespostaNumerica note) {
            respostes.add(note);
        }

    public void setRespostes(ArrayList<RespostaNumerica> respostes) {
        this.respostes = respostes;
    }
    public ArrayList<RespostaNumerica> getRespostes() {
        return this.respostes;
    }


    public RespostaNumerica getSeguent() {


        for (RespostaNumerica r : this.respostes ) {
            if (r.getValor() == 0)
                return r;
        }
        return  null;
    }
}
