package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.repository.CoachRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coaches")
public class CoachController {

    private CoachRepository coachRepository;

    public CoachController(CoachRepository theCoachRepository){
        coachRepository=theCoachRepository;
    }

   @GetMapping("/list")
    public String listCoaches(Model theModel){
       theModel.addAttribute("messages",coachRepository.findAll());
       return "list-coaches";
   }
}
