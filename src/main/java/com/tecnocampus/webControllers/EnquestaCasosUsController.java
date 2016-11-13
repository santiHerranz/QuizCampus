package com.tecnocampus.webControllers;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.exceptions.EnquestaDuplicadaException;
import com.tecnocampus.security.SecurityService;
import com.tecnocampus.useCases.EnquestaCasosUs;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by santi on 14/10/2016.
 */
@Controller
@RequestMapping("/")
public class EnquestaCasosUsController {

    private SecurityService securityService;
    private EnquestaCasosUs enquestaCasosUs;


    public EnquestaCasosUsController(EnquestaCasosUs enquestaCasosUs, SecurityService securityService) {
        this.enquestaCasosUs = enquestaCasosUs;
        this.securityService = securityService;
    }


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

    @GetMapping("enquestes/{enquestaId}/edita")
    public String editItem(@PathVariable("enquestaId") Long enquestaId, Model model) {

        Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
        model.addAttribute(enquesta);
        return "enquestaForm";
    }


    @PostMapping("enquestes/{enquestaId}/edita")
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


        @PostMapping("enquestes/{enquestaId}/esborra")
    public String processDeleteUser(@PathVariable("enquestaId") Long enquestaId,
                                    final RedirectAttributes redirectAttributes) {

            Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);

        try {
            enquestaCasosUs.eliminarEnquesta(enquesta);

            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", enquesta.getTitol() +" esborrat!");

            return "redirect:/enquestes";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", enquesta.getTitol() +" "+ e.getMessage());

            return "redirect:/enquestes";
        }
    }




    @PostMapping("preguntes/{preguntaId}/esborra")
    public String processPreguntaUser(@PathVariable("preguntaId") Long preguntaId,
                                    final RedirectAttributes redirectAttributes) {

        Pregunta pregunta = enquestaCasosUs.obtenirPregunta(preguntaId);

        try {
            enquestaCasosUs.eliminarPregunta(pregunta);

            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", pregunta.getEnunciat() +" esborrat!");

            return "redirect:/enquestes/"+ pregunta.getEnquesta().getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", pregunta.getEnunciat() +" "+ e.getMessage());

            return "redirect:/enquestes/"+ pregunta.getEnquesta().getId();
        }
    }

}
