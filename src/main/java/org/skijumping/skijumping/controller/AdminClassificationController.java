package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Clasification;
import org.skijumping.skijumping.repository.ClasificationRepository;
import org.skijumping.skijumping.repository.TourneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/clasification")
public class AdminClassificationController {
    ClasificationRepository clasificationRepository;
    TourneeRepository tourneeRepository;

    @Autowired
    public AdminClassificationController(ClasificationRepository clasificationRepository, TourneeRepository tourneeRepository) {
        this.clasificationRepository = clasificationRepository;
        this.tourneeRepository = tourneeRepository;
    }

    @GetMapping("/")
    public String clasificationList (Model theModel){
        List<Clasification> clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(2).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("clasifications",clas );
        return "admin/clasification";
    }
}
