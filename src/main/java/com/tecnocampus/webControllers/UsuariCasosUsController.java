package com.tecnocampus.webControllers;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.ContrasenyaNoValidaException;
import com.tecnocampus.exceptions.UsuariDuplicatException;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

    @GetMapping("registre")
    public String createUser(Model model) {
        model.addAttribute(new Usuari());
        return "usuariForm";
    }

    @PostMapping("registre")
    public String processCreateUser(@Valid Usuari user, Errors errors, Model model, BindingResult result , RedirectAttributes redirectAttributes) {
        if (errors.hasErrors())
            return "usuariForm";

        try {
            usuariCasosUs.crearUsuari(user.getEmail(), user.getContrasenya());

        } catch (ContrasenyaNoValidaException e) {
            ObjectError error = new ObjectError("contrasenya","Error contrasenya no es vàlida");
            result.addError(error);
            return "usuariForm";

        } catch (UsuariDuplicatException e) {
            ObjectError error = new ObjectError("email","Error email ja existeix");
            result.addError(error);
            return "usuariForm";
        }

        redirectAttributes.addAttribute("id", user.getId());
        //return "redirect:usuaris/{id}"; //in this way username is scaped and dangerous chars changed
        return "redirect:usuaris"; //in this way username is scaped and dangerous chars changed
    }

}
