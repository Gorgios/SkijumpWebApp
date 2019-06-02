package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Clasification;
import org.skijumping.skijumping.model.Jumper;
import org.skijumping.skijumping.model.Tournee;
import org.skijumping.skijumping.repository.ClasificationRepository;
import org.skijumping.skijumping.repository.JumperRepository;
import org.skijumping.skijumping.repository.TeamRepository;
import org.skijumping.skijumping.repository.TourneeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("admin/jumpers")
public class AdminJumperController {

    private JumperRepository jumperRepository;
    private TeamRepository teamRepository;
    private ClasificationRepository clasificationRepository;
    private TourneeRepository tourneeRepository;

    public AdminJumperController(JumperRepository jumperRepository, TeamRepository teamRepository, ClasificationRepository clasificationRepository,
                                 TourneeRepository tourneeRepository) {
        this.jumperRepository = jumperRepository;
        this.teamRepository = teamRepository;
        this.tourneeRepository = tourneeRepository;
        this.clasificationRepository = clasificationRepository;
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
        for (Tournee t : tourneeRepository.findAll()) {
            Clasification  clasification = new Clasification();
            clasification.setJumper(jumper);
            clasification.setTournee(t);
            clasification.setPoints(0);
            clasificationRepository.save(clasification);
        }
        return "redirect:/admin/jumpers/";
   }
    @PostMapping("/delete")
    public String delete(@RequestParam("jumperId") int theId) {

        jumperRepository.deleteById(theId);
        return "redirect:/admin/jumpers/";

    }
    @GetMapping("/hehe")
    public ModelAndView dodaj(@RequestParam("jumperId") int theId,
                              Model theModel) {
        Jumper jumper = jumperRepository.findById(theId).orElse(null);
        jumper.setFirstName(jumper.getFirstName() + "XD");
        return new ModelAndView ("hehe","jumperModel",theModel);
    }
    }
