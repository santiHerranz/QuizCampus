package com.tecnocampus.webControllers;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by santi on 14/10/2016.
 */
@Controller
public class UsuariCasosUsController {

    @Autowired
    UsuariCasosUs usuariCasosUs;

    @GetMapping("usuaris")
    public List<Usuari> listNotes() {
        return usuariCasosUs.llistarUsuaris();
    }

    @GetMapping("usuaris/{usuariId}")
    public String showUser(@PathVariable("usuariId") Long usuariId, Model model) {
        //we're going to ask to UserUseCases for a user only if the model
        //doesn't already carry one (from a redirect)
        if (!model.containsAttribute("usuari")) {
            model.addAttribute("usuari",usuariCasosUs.cercarUsuari(usuariId));
        }
        return "usuari";
    }

}
