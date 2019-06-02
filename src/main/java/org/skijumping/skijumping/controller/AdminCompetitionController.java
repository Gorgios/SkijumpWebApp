package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.*;
import org.skijumping.skijumping.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/competitions")
public class AdminCompetitionController {

    private ClasificationRepository clasificationRepository;
    private CompetitionRepository competitionRepository;
    private HillRepository hillRepository;
    private TourneeRepository tourneeRepository;
    private StartRepository startRepository;
    private JumperRepository jumperRepository;

    public AdminCompetitionController(CompetitionRepository competitionRepository,
                                      StartRepository startRepository, HillRepository hillRepository,
                                      TourneeRepository tourneeRepository, JumperRepository jumperRepository,
                                      ClasificationRepository clasificationRepository) {
        this.competitionRepository = competitionRepository;
        this.hillRepository = hillRepository;
        this.tourneeRepository = tourneeRepository;
        this.startRepository = startRepository;
        this.jumperRepository = jumperRepository;
        this.clasificationRepository = clasificationRepository;
    }

    @GetMapping("/")
    public String listCompetitions(Model theModel){
       theModel.addAttribute("competitions" , competitionRepository.findAll());
       return "admin/competition";
   }
    @GetMapping("/addCompetition")
    public String addCompetition(Model theModel) {
        Competition competition = new Competition();

        theModel.addAttribute("tournees", tourneeRepository.findAll());
        theModel.addAttribute("hills",hillRepository.findAll());
        theModel.addAttribute("competition",competition);
        return "admin/add-competitions";
    }

    @PostMapping("/save")
    public String saveCompetition(@ModelAttribute("competition") Competition competition){
        zawody(competition);
        List<Start> starts = competition.getStarts();
        List <Clasification> clasification = (List<Clasification>) clasificationRepository.findAll();;
        Collections.sort(starts);
        Collections.reverse(starts);
        for (Tournee t: competition.getTournees()) {
            for (Clasification c : clasification) {
                if (c.getTournee() == t)
                    for (int i = 0; i < starts.size(); i++) {
                        c.makePoints(i+1);
                    }
            }
        }
        competitionRepository.save(competition);

        return "redirect:/admin/competitions/";
    }
    @RequestMapping("/showCompetition")
    public String showCompetition(@RequestParam("competitionId") int theId,
                             Model theModel) {
        Competition competition = competitionRepository.findById(theId).orElse(null);

        List<Start> starts = competition.getStarts();
        Collections.sort(starts,Collections.reverseOrder());
        theModel.addAttribute("starts",starts);
        return "admin/competition-single";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("competitionId") int theId) {

        competitionRepository.deleteById(theId);
        return "redirect:/admin/competitions/";

    }

    public void zawody(Competition competition){
        Start start;
        for (Jumper jumper : jumperRepository.findAll() ){
            start = jumper.makeStart(competition);
            startRepository.save(start);
        }
    }

}
