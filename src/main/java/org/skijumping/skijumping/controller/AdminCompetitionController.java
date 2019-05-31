package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.*;
import org.skijumping.skijumping.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("admin/competitions")
public class AdminCompetitionController {

    private CompetitionRepository competitionRepository;
    private HillRepository hillRepository;
    private TourneeRepository tourneeRepository;
    private StartRepository startRepository;
    private JumperRepository jumperRepository;

    public AdminCompetitionController(CompetitionRepository competitionRepository,
                                      StartRepository startRepository, HillRepository hillRepository,
                                      TourneeRepository tourneeRepository, JumperRepository jumperRepository) {
        this.competitionRepository = competitionRepository;
        this.hillRepository = hillRepository;
        this.tourneeRepository = tourneeRepository;
        this.startRepository = startRepository;
        this.jumperRepository = jumperRepository;
    }

    @GetMapping("/")
    public String listCompetitions(Model theModel){
       theModel.addAttribute("competitions" , competitionRepository.findAll());
       return "admin/competition";
   }
    @GetMapping("/addCompetition")
    public String addCompetition(Model theModel) {
        Competition competition = new Competition();
        theModel.addAttribute("competition",competition);
        theModel.addAttribute("tournees", tourneeRepository.findAll());
        theModel.addAttribute("hills",hillRepository.findAll());
        return "admin/add-competition";
    }

    @PostMapping("/save")
    public String saveCompetition(@ModelAttribute("competition") Competition competition){
        competitionRepository.save(competition);
        return "redirect:/admin/competitions/";
    }
    @RequestMapping("/zawody")
    public String zawody(){
        Competition competition = competitionRepository.findById(1).orElse(null);
        Start start;
        for (Jumper jumper : jumperRepository.findAll() ){
            start = jumper.makeStart(competition);
            startRepository.save(start);
        }
        return "admin/competitions/";
    }

}
