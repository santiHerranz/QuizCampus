package com.tecnocampus.webControllers;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.exceptions.EnquestaDuplicadaException;
import com.tecnocampus.useCases.EnquestaCasosUs;
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
public class EnquestaCasosUsController {

    @Autowired
    EnquestaCasosUs enquestaCasosUs;



    @GetMapping("enquestes")
    public List<Enquesta> listNotes() {
        return enquestaCasosUs.llistarEnquestes();
    }

    @GetMapping("enquestes/{enquestaId}")
    public String showUser(@PathVariable("enquestaId") Long enquestaId, Model model) {
        //we're going to ask to UserUseCases for a user only if the model
        //doesn't already carry one (from a redirect)
        if (!model.containsAttribute("enquesta")) {
            model.addAttribute("enquesta",enquestaCasosUs.obetenirEnquesta(enquestaId));
        }
        return "enquesta";
    }

    @GetMapping("enquestes/nova")
    public String createUser(Model model) {
        model.addAttribute(new Enquesta());
        return "enquestaForm";
    }

    @PostMapping("enquestes/nova")
    public String processCreateUser(@Valid Enquesta enquesta, Errors errors, Model model, BindingResult result , RedirectAttributes redirectAttributes) {
        if (errors.hasErrors())
            return "enquestaForm";

        try {
            enquestaCasosUs.crearEnquesta(enquesta.getTitol());

        } catch (EnquestaDuplicadaException e) {
            ObjectError error = new ObjectError("titol","Error l'enquesta ja existeix");
            result.addError(error);
            return "enquestaForm";

        }

        redirectAttributes.addAttribute("id", enquesta.getId());
        //return "redirect:enquestes/{id}"; //in this way username is scaped and dangerous chars changed
        return "redirect:/enquestes"; //in this way username is scaped and dangerous chars changed
    }

}
