package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.*;
import org.skijumping.skijumping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/fis")
public class FisController {


    private UserRepository userRepository;
    private ClasificationRepository clasificationRepository;
    private CompetitionRepository competitionRepository;
    private HillRepository hillRepository;
    private TourneeRepository tourneeRepository;
    private StartRepository startRepository;
    private JumperRepository jumperRepository;

    @Autowired
    public FisController(UserRepository userRepository, ClasificationRepository clasificationRepository,
                         CompetitionRepository competitionRepository, HillRepository hillRepository,
                         TourneeRepository tourneeRepository, StartRepository startRepository, JumperRepository jumperRepository) {
        this.userRepository = userRepository;
        this.clasificationRepository = clasificationRepository;
        this.competitionRepository = competitionRepository;
        this.hillRepository = hillRepository;
        this.tourneeRepository = tourneeRepository;
        this.startRepository = startRepository;
        this.jumperRepository = jumperRepository;
    }


    @GetMapping("/")
    public String jumperHomePage(Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        theModel.addAttribute("competitions",competitionRepository.findAll());
        List<Clasification> clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(2).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("worldCup",clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(3).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("tcs",  clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(4).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("w5",  clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(5).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("rawAir",  clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(6).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("planica7",  clas);
        return "fis/index";
    }
    @GetMapping("/competitions")
    public String competition(Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        theModel.addAttribute("competitions",competitionRepository.findAll());
        return "fis/competitions";
    }
    @GetMapping("/addCompetition")
    public String addCompetition(Model theModel, Principal principal) {
        Competition competition = new Competition();
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        theModel.addAttribute("tournees", tourneeRepository.findAll());
        theModel.addAttribute("hills",hillRepository.findAll());
        theModel.addAttribute("competition",competition);
        return "fis/add-competitions";
    }
    @RequestMapping("/showCompetition")
    public String showCompetition(@RequestParam("competitionId") int theId,
                                  Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        Competition competition = competitionRepository.findById(theId).orElse(null);

        List<Start> starts = competition.getStarts();
        Collections.sort(starts,Collections.reverseOrder());
        theModel.addAttribute("starts",starts);
        return "fis/competition-single";
    }
    @PostMapping("/save")
    public String doCompetition (@ModelAttribute("competition") Competition competition){
        zawody(competition);
        int idComp= competition.getId();
        competitionRepository.save(competition);
        Competition comp = competitionRepository.findById(idComp).orElse(null);
        List<Start> starts = startRepository.findAllByCompetition(comp);
        Collections.sort(starts,Collections.reverseOrder());
        starts.get(0).getJumper().setCredits(starts.get(0).getJumper().getCredits()+5);
        starts.get(1).getJumper().setCredits(starts.get(1).getJumper().getCredits()+3);
        starts.get(2).getJumper().setCredits(starts.get(2).getJumper().getCredits()+1);
        Clasification clas;
        for (Tournee t: comp.getTournees()) {
            for (int i = 0; i < starts.size(); i++) {
                clas = clasificationRepository.findByJumperAndTournee(starts.get(i).getJumper(), t);
                clas.makePoints(i + 1);
                clasificationRepository.save(clas);
            }
        }
        return "redirect:/fis/";
    }
    @PostMapping("/deleteCompetition")
    public String delete(@RequestParam("competitionId") int theId) {

        competitionRepository.deleteById(theId);
        return "redirect:/fis/competitions";

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
