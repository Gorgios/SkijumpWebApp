package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @RequestMapping("/")
    public String homePage(Model theModel, Principal principal, Authentication auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        return "admin/index";
    }
}