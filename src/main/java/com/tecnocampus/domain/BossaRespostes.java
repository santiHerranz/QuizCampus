package com.tecnocampus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santi on 25/10/16.
 */
public class BossaRespostes implements Serializable {
        private ArrayList<Resposta> respostes;

        public BossaRespostes() {
            this.respostes = new ArrayList<>();
        }

        public void addResposta(Resposta note) {
            System.out.println(((RespostaNumerica)note).getValor());
            respostes.add(note);
        }

        public List<Resposta> getRespostes() {
            return respostes;
        }
    
}
