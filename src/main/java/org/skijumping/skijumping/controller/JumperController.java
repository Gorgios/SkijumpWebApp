package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Jumper;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.JumperRepository;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/jumper")
public class JumperController {
    private JumperRepository jumperRepository;
    private UserRepository userRepository;

    @Autowired
    public JumperController(JumperRepository jumperRepository, UserRepository userRepository) {
        this.jumperRepository = jumperRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String jumperHomePage(Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        Jumper jumper = jumperRepository.findByUser(user);
        theModel.addAttribute("user",user);
        theModel.addAttribute("jumper",jumper);
        return "jumper/index";
    }
    @GetMapping("/myData")
    public String myData (Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        Jumper jumper = jumperRepository.findByUser(user);
        theModel.addAttribute("user",user);
        theModel.addAttribute("jumper",jumper);
        return "jumper/myData";
    }

    @GetMapping("/training")
    public String makeTraining(Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Jumper jumper = jumperRepository.findByUser(user);
        theModel.addAttribute("user",user);
        theModel.addAttribute("jumper",jumper);
        return "jumper/training";}
    @GetMapping("/showData")
    public String showData(Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        Jumper jumper = jumperRepository.findByUser(user);
        theModel.addAttribute("user",user);
        theModel.addAttribute("message",jumper);
        return "jumper/jumper-data";
    }
        @PostMapping("/doTrain")
    public String doTrain(@RequestParam("jumperId") int theId){
        Jumper jumper = jumperRepository.findById(theId).orElse(null);
        jumper.makeTraining();
        jumperRepository.save(jumper);
        return "redirect:/jumper/training";
    }
}
