package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.*;
import org.skijumping.skijumping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    private UserRepository userRepository;

    @Autowired
    public AdminCompetitionController(CompetitionRepository competitionRepository,
                                      StartRepository startRepository, HillRepository hillRepository,
                                      TourneeRepository tourneeRepository, JumperRepository jumperRepository,
                                      ClasificationRepository clasificationRepository, UserRepository userRepository) {
        this.competitionRepository = competitionRepository;
        this.hillRepository = hillRepository;
        this.tourneeRepository = tourneeRepository;
        this.startRepository = startRepository;
        this.jumperRepository = jumperRepository;
        this.clasificationRepository = clasificationRepository;
        this.userRepository = userRepository;

    }

    @GetMapping("/")
    public String listCompetitions(Model theModel , Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
       theModel.addAttribute("competitions" , competitionRepository.findAll());
       return "admin/competition";
   }
    @GetMapping("/addCompetition")
    public String addCompetition(Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        Competition competition = new Competition();

        theModel.addAttribute("tournees", tourneeRepository.findAll());
        theModel.addAttribute("hills",hillRepository.findAll());
        theModel.addAttribute("competition",competition);
        return "admin/add-competitions";
    }

    @PostMapping("/save")
    public String saveCompetition(@Valid @ModelAttribute("competition") Competition competition, BindingResult bindingResult, Model theModel){
        if (bindingResult.hasErrors()){
            theModel.addAttribute("tournees", tourneeRepository.findAll());
            theModel.addAttribute("hills",hillRepository.findAll());
            return "admin/add-competitions";
        }
        else {
            zawody(competition);
            int idComp = competition.getId();
            competitionRepository.save(competition);
            Competition comp = competitionRepository.findById(idComp).orElse(null);
            List<Start> starts = startRepository.findAllByCompetition(comp);
            Collections.sort(starts, Collections.reverseOrder());
            for (Start s: starts)
                s.getJumper().setCredits(s.getJumper().getCredits()+2);
            Clasification clas;
            for (Tournee t : comp.getTournees()) {
                for (int i = 0; i < starts.size(); i++) {
                    clas = clasificationRepository.findByJumperAndTournee(starts.get(i).getJumper(), t);
                    clas.makePoints(i + 1);
                    clasificationRepository.save(clas);
                }
            }
            return "redirect:/admin/competitions/";
        }
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
            System.out.println(start);
            startRepository.save(start);
        }

    }

}
