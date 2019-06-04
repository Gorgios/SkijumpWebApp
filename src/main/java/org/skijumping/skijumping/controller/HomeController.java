package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/403")
    public String get403Page() {
        return "error/403";
    }
    @GetMapping("/loginSuccess")
    public String successLogin( Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:/admin/";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("JUMPER"))) {
            return "redirect:/jumper/";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("FIS"))) {
            return "redirect:/fis/";
        }
        return "redirect:/";
    }
    @GetMapping("/loginError")
    public ModelAndView loginError(ModelAndView mav, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginError", true);
        mav.setViewName("redirect:/");
        return mav;
    }
}
