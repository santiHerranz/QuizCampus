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

            // comprobem que l'enquesta existeix, en cas contrari mostrem llistat
            Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
            if (enquesta == null)
                return "redirect:/enquestes";
            model.addAttribute("enquesta",enquesta);
        }
        return "enquesta";
    }

    @GetMapping("enquestes/nova")
    public String createItem(Model model) {
        model.addAttribute(new Enquesta());
        return "enquestaForm";
    }

    @GetMapping("enquestes/edita/{enquestaId}")
    public String editItem(@PathVariable("enquestaId") Long enquestaId, Model model) {

        Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
        model.addAttribute(enquesta);
        return "enquestaForm";
    }


    @PostMapping("enquestes/edita/{enquestaId}")
    public String processEditItem(@Valid Enquesta enquesta, Errors errors, Model model, BindingResult result , RedirectAttributes redirectAttributes) {

        if (errors.hasErrors())
            return "enquestaForm";

        try {
            enquesta = enquestaCasosUs.save(enquesta);

        } catch (EnquestaDuplicadaException e) {
            ObjectError error = new ObjectError("titol","Error l'enquesta ja existeix");
            result.addError(error);
            return "enquestaForm";
        }

        redirectAttributes.addAttribute("id", enquesta.getId());
        redirectAttributes.addFlashAttribute("enquesta", enquesta);
        return "redirect:/enquestes/{id}";
    }


    @PostMapping("enquestes/nova")
    public String processCreateUser(@Valid Enquesta enquesta, Errors errors, Model model, BindingResult result , RedirectAttributes redirectAttributes) {

        if (errors.hasErrors())
            return "enquestaForm";

        try {
            enquesta = enquestaCasosUs.crearEnquesta(enquesta.getTitol());

        } catch (EnquestaDuplicadaException e) {
            ObjectError error = new ObjectError("titol","Error l'enquesta ja existeix");
            result.addError(error);
            return "enquestaForm";

        }

        redirectAttributes.addAttribute("id", enquesta.getId());
        redirectAttributes.addFlashAttribute("enquesta", enquesta);
        return "redirect:/enquestes/{id}";

    }

    @PostMapping("enquestes/esborra/{enquestaId}")
    public String processDeleteItem(@PathVariable("enquestaId") Long enquestaId) {

        Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
        enquestaCasosUs.eliminarEnquesta(enquesta);

        return "redirect:/enquestes";
    }

    }
