package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Clasification;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.ClasificationRepository;
import org.skijumping.skijumping.repository.TourneeRepository;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/clasification")
public class AdminClassificationController {
    ClasificationRepository clasificationRepository;
    TourneeRepository tourneeRepository;
    UserRepository userRepository;
    @Autowired
    public AdminClassificationController(ClasificationRepository clasificationRepository, TourneeRepository tourneeRepository, UserRepository userRepository) {
        this.clasificationRepository = clasificationRepository;
        this.tourneeRepository = tourneeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String clasificationList (@RequestParam("classificationId") int theId,Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        List<Clasification> clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(theId).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("clasifications",clas );
        return "admin/clasification";
    }
}
