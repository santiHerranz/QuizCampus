package com.tecnocampus.webControllers;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.exceptions.EnquestaDuplicadaException;
import com.tecnocampus.security.SecurityService;
import com.tecnocampus.useCases.EnquestaCasosUs;
import com.tecnocampus.useCases.PreguntaCasosUs;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by santi on 14/10/2016.
 */
@Controller
@RequestMapping("/")
public class EnquestaController {

    private SecurityService securityService;
    private EnquestaCasosUs enquestaCasosUs;
    private PreguntaCasosUs preguntaCasosUs;


    public EnquestaController(EnquestaCasosUs enquestaCasosUs, SecurityService securityService, PreguntaCasosUs preguntaCasosUs) {
        this.enquestaCasosUs = enquestaCasosUs;
        this.securityService = securityService;
        this.preguntaCasosUs = preguntaCasosUs;
    }

    @GetMapping("enquestes")
    public List<Enquesta> listEnquestes() {
        return enquestaCasosUs.llistarEnquestes();
    }

    @GetMapping("enquestes/{enquestaId}")
    public String showEnquesta(@PathVariable("enquestaId") Long enquestaId, Model model) {

        if (!model.containsAttribute("enquesta")) {
            // comprobem que l'enquesta existeix, en cas contrari mostrem llistat
            Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
            if (enquesta == null)
                return "redirect:/enquestes";
            model.addAttribute("enquesta",enquesta);
        }
        return "enquesta";
    }

    @GetMapping("admin/enquestes")
    public List<Enquesta> listAdminEnquestes() {
        return enquestaCasosUs.llistarEnquestes();
    }

    @GetMapping("/admin/enquestes/{enquestaId}")
    public String showAdminEnquesta(@PathVariable("enquestaId") Long enquestaId, Model model) {
        //we're going to ask to UserUseCases for a user only if the model
        //doesn't already carry one (from a redirect)
        if (!model.containsAttribute("enquesta")) {

            // comprobem que l'enquesta existeix, en cas contrari mostrem llistat
            Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
            if (enquesta == null)
                return "redirect:/enquestes";
            model.addAttribute("enquesta",enquesta);
        }
        return "/admin/enquesta";
    }

    @GetMapping("/admin/enquestes/nova")
    public String createItem(Model model) {
        model.addAttribute(new Enquesta());
        return "/admin/enquestaForm";
    }

    @GetMapping("/admin/enquestes/{enquestaId}/edita")
    public String editItem(@PathVariable("enquestaId") Long enquestaId, Model model) {

        Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
        model.addAttribute(enquesta);
        return "/admin/enquestaForm";
    }

    class orderedKeyList {
        String list;
    }


    @GetMapping("/admin/preguntes")
    public List<Pregunta> listpreguntes() {
        return preguntaCasosUs.llistarPreguntes();
    }

    @GetMapping("/admin/preguntes/{preguntaId}")
    public String preguntaDetall(@PathVariable("preguntaId") Long preguntaId, Model model) {
        Pregunta pregunta = preguntaCasosUs.obtenirPregunta(preguntaId);
        model.addAttribute("pregunta", pregunta);
        return "/admin/pregunta";
    }

    @PostMapping("/admin/preguntes/{preguntaId}")
    public String processPregunta() {
        return null;
    }


    @GetMapping("admin_enquestes/{enquestaId}/reordena")
    public String ordenaItem(@PathVariable("enquestaId") Long enquestaId, Model model) {
        Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);
        model.addAttribute(enquesta);
        model.addAttribute(new orderedKeyList());
        return "admin_enquestaReordenaPreguntes";
    }

    @PostMapping("admin_enquestes/{enquestaId}/reordena")
    public String processReordenaItem(@PathVariable("enquestaId") Long enquestaId, @RequestParam("orderedKeyList") String orderedKeyList, RedirectAttributes redirectAttributes) {

        Enquesta enquesta = enquestaCasosUs.obtenirEnquesta(enquestaId);

        //String orderedKeyList = "4,3,2,1";
        enquestaCasosUs.reordenarPreguntes(enquesta, orderedKeyList);

        redirectAttributes.addAttribute("id", enquesta.getId());
        redirectAttributes.addFlashAttribute("enquesta", enquesta);
        return "redirect:/enquestes/{id}";
    }


    @PostMapping("admin_enquestes/{enquestaId}/edita")
    public String processEditItem(@Valid Enquesta enquesta, Errors errors, Model model, BindingResult result , RedirectAttributes redirectAttributes) {

        if (errors.hasErrors())
            return "admin_enquestaForm";

        try {
            enquesta = enquestaCasosUs.save(enquesta);

        } catch (EnquestaDuplicadaException e) {
            ObjectError error = new ObjectError("titol","Error l'enquesta ja existeix");
            result.addError(error);
            return "admin_enquestaForm";
        }

        redirectAttributes.addAttribute("id", enquesta.getId());
        redirectAttributes.addFlashAttribute("enquesta", enquesta);
        return "redirect:/enquestes/{id}";
    }


    @PostMapping("admin_enquestes/nova")
    public String processCreateEnquesta(@Valid Enquesta enquesta, Errors errors, Model model, BindingResult result , RedirectAttributes redirectAttributes) {

        if (errors.hasErrors())
            return "admin_enquestaForm";

        try {
            enquesta = enquestaCasosUs.crearEnquesta(enquesta.getTitol());

        } catch (EnquestaDuplicadaException e) {
            ObjectError error = new ObjectError("titol","Error l'enquesta ja existeix");
            result.addError(error);
            return "admin_enquestaForm";

        }

        redirectAttributes.addAttribute("id", enquesta.getId());
        redirectAttributes.addFlashAttribute("enquesta", enquesta);
        return "redirect:/enquestes/{id}";

    }


        @PostMapping("admin_enquestes/{enquestaId}/esborra")
    public String processDeleteEnquesta(@PathVariable("enquestaId") Long enquestaId,
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
    public String processPreguntaDelete(@PathVariable("preguntaId") Long preguntaId,
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
