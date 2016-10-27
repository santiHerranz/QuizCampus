package com.tecnocampus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santi on 25/10/16.
 */
public class BossaPreguntes implements Serializable {
        private ArrayList<Pregunta> preguntes;

        public BossaPreguntes() {
            this.preguntes = new ArrayList<>();
        }

        public void addPregunta(Pregunta note) {
            System.out.println(note.getEnunciat());
            preguntes.add(note);
        }

        public List<Pregunta> getPreguntes() {
            return preguntes;
        }
    
}
