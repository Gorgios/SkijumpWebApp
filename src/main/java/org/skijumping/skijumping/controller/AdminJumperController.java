package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Jumper;
import org.skijumping.skijumping.repository.JumperRepository;
import org.skijumping.skijumping.repository.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("admin/jumpers")
public class AdminJumperController {

    private JumperRepository jumperRepository;
    private TeamRepository teamRepository;


    public AdminJumperController(JumperRepository jumperRepository, TeamRepository teamRepository) {
        this.jumperRepository = jumperRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/")
    public String listCoaches(Model theModel){
       theModel.addAttribute("messages",jumperRepository.findAll());
       return "admin/jumpers";
   }
    @GetMapping("/addJumper")
    public String addJumper(Model theModel) {

        Jumper jumper = new Jumper();
        theModel.addAttribute("teams",teamRepository.findAll());
        theModel.addAttribute("jumper",jumper);
        return "admin/add-jumper";
    }
    @PostMapping("/updateJumper")
    public String updateJumper(@RequestParam("jumperId") int theId,
                                    Model theModel) {

        Optional<Jumper> jumper = jumperRepository.findById(theId);
        theModel.addAttribute("jumper", jumper);
        return "admin/add-jumper";
    }

    @PostMapping("/save")
    public String saveJumper(@ModelAttribute("jumper") Jumper jumper) {
        jumperRepository.save(jumper);
        return "redirect:/admin/jumpers/";
   }
    @PostMapping("/delete")
    public String delete(@RequestParam("jumperId") int theId) {

        jumperRepository.deleteById(theId);
        return "redirect:/admin/jumpers/";

    }
}
