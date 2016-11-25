package com.tecnocampus.webControllers;

import com.tecnocampus.domain.Usuari;
import com.tecnocampus.exceptions.ContrasenyaNoValidaException;
import com.tecnocampus.exceptions.UsuariDuplicatException;
import com.tecnocampus.security.SecurityService;
import com.tecnocampus.useCases.UsuariCasosUs;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by santi on 14/10/2016.
 */
@Controller
public class UsuariController {

    private UsuariCasosUs usuariCasosUs;
    private SecurityService securityService;

    public UsuariController(UsuariCasosUs usuariCasosUs, SecurityService securityService){

        this.usuariCasosUs = usuariCasosUs;
        this.securityService = securityService;
    }



    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("admin/usuaris")
    public List<Usuari> listNotes() {
        return usuariCasosUs.llistarUsuaris();
    }

    @GetMapping("admin/usuaris/{usuariId}")
    public String showUser(@PathVariable("usuariId") Long usuariId, Model model) {
        //we're going to ask to UserUseCases for a user only if the model
        //doesn't already carry one (from a redirect)
        if (!model.containsAttribute("usuari")) {

            // comprobem que l'usuari existeix, en cas contrari mostrem llistat
            Usuari usuari = usuariCasosUs.cercarUsuari(usuariId);
            if (usuari == null)
                return "redirect:/admin/usuaris";

            model.addAttribute("usuari",usuari);
        }
        return "admin/usuari";
    }


    @PostMapping("admin/usuaris/{usuariId}/esborra")
    public String processDeleteUser(@PathVariable("usuariId") Long usuariId,
                                    final RedirectAttributes redirectAttributes) {

        Usuari user = usuariCasosUs.cercarUsuari(usuariId);

        try {
            usuariCasosUs.eliminarUsuari(user);

            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", user.getUsername() +" esborrat!");

            return "redirect:/admin/usuaris";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", user.getUsername() +" "+ e.getMessage());

            return "redirect:/admin/usuaris";
        }
    }



    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("logout")
    public String logoutPage() {
        SecurityContextHolder.clearContext();
        return "logout";
    }

    @GetMapping("registre")
    public String createUser(Model model) {
        SecurityContextHolder.clearContext();
        model.addAttribute(new Usuari());
        return "registre";
    }

    @PostMapping("registre")
    public String processCreateUser(@Valid Usuari user, Errors errors, Model model, BindingResult result , RedirectAttributes redirectAttributes) {

        if (errors.hasErrors())
            return "registre";

        try {

            String username = user.getUsername();
            String plainPassword = user.getPassword();

            user = this.usuariCasosUs.crearUsuari(username, plainPassword);
            this.securityService.login(username, plainPassword);
            return "redirect:/";

        } catch (ContrasenyaNoValidaException e) {

            for (String missatgeError : e.getMessage().split("&&")) {
                ObjectError error = new ObjectError("contrasenya", missatgeError);
                result.addError(error);
            }

            return "registre";

        }
        catch (UsuariDuplicatException e) {
            ObjectError error = new ObjectError("email","Error email ja existeix");
            result.addError(error);
            return "registre";
        }

        //redirectAttributes.addAttribute("id", user.getId());
        //redirectAttributes.addFlashAttribute("usuari", user);
        //return "redirect:/";

    }






    @GetMapping("profile")
    public String profilePage(Model model) {

        Usuari usuari = usuariCasosUs.cercarUsuari(getPrincipal());
        model.addAttribute("usuari",usuari);

        return "profile";
    }

    /*
    This method is called whenever a UserLabUsernameAlreadyExistsException is signalled from any of the
    @RequestMapping annotated methods in this controller.
    We can have Advising Controllers that handle exceptions from all the controllers (no just one).
    The advising controllers must be annotated with @ControllerAdvice and have one or more methods annotated
    with @ExceptionHandler
     */
    @ExceptionHandler(UsuariDuplicatException.class)
    public String handleUsernameAlreadyExists(Model model, HttpServletRequest request) {
        model.addAttribute("username", request.getAttribute("username"));
        return "error/usernameAlreadyExists";
    }


    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
