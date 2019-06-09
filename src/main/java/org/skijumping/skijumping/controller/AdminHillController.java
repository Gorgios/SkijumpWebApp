package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Coach;
import org.skijumping.skijumping.model.Hill;
import org.skijumping.skijumping.model.Team;
import org.skijumping.skijumping.repository.CoachRepository;
import org.skijumping.skijumping.repository.HillRepository;
import org.skijumping.skijumping.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/hills")
public class AdminHillController {

   private HillRepository hillRepository;

   @Autowired
    public AdminHillController(HillRepository hillRepository) {
        this.hillRepository = hillRepository;
    }

    @GetMapping("/")
    public String listHills(Model theModel){
       theModel.addAttribute("messages",hillRepository.findAll());
       return "admin/hills";
   }
    @GetMapping("/addHill")
    public String addHill(Model theModel) {

     Hill hill = new Hill();
     theModel.addAttribute("hill", hill);
       return "admin/add-hill";
    }
    @PostMapping("/updateHill")
    public String updateTeam(@RequestParam("hillId") int theId,
                                    Model theModel) {

       Optional<Hill> hill = hillRepository.findById(theId);
       theModel.addAttribute("hill",hill);
        return "admin/add-hill";
    }

    @PostMapping("/save")
    public String saveTeam(@Valid  @ModelAttribute("hill") Hill hill, BindingResult bindingResult){
       if (bindingResult.hasErrors()){
           return "admin/add-hill";
       }
       else {
           hillRepository.save(hill);
           return "redirect:/admin/hills/";
       }
   }
    @PostMapping("/delete")
    public String delete(@RequestParam("hillId") int theId) {

        hillRepository.deleteById(theId);

        return "redirect:/admin/hills/";

    }
 }
