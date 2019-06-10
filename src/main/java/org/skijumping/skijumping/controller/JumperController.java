package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.*;
import org.skijumping.skijumping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jumper")
public class JumperController {
    private JumperRepository jumperRepository;
    private UserRepository userRepository;
    private ClasificationRepository clasificationRepository;
    private TourneeRepository tourneeRepository;
    private CompetitionRepository competitionRepository;

    @Autowired
    public JumperController(JumperRepository jumperRepository, UserRepository userRepository, ClasificationRepository clasificationRepository,
                            TourneeRepository tourneeRepository, CompetitionRepository competitionRepository) {
        this.jumperRepository = jumperRepository;
        this.userRepository = userRepository;
        this.clasificationRepository = clasificationRepository;
        this.tourneeRepository = tourneeRepository;
        this.competitionRepository = competitionRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String jumperHomePage(Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        Jumper jumper = jumperRepository.findByUser(user);
        theModel.addAttribute("user",user);
        theModel.addAttribute("jumper",jumper);
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
    @RequestMapping("/showCompetition")
    public String showCompetition(@RequestParam("competitionId") int theId,
                                  Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        Competition competition = competitionRepository.findById(theId).orElse(null);

        List<Start> starts = competition.getStarts();
        Collections.sort(starts,Collections.reverseOrder());
        theModel.addAttribute("starts",starts);
        return "jumper/competition-single";
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
        user.setPassword("");
        theModel.addAttribute("user",user);
        theModel.addAttribute("message",jumper);
        return "jumper/jumper-data";
    }
    @PostMapping("/updateJumper")
    public String updateUser(@Valid @ModelAttribute("user") User user,  BindingResult bindingResult){
        if (bindingResult.hasErrors()){

            return "jumper/jumper-data";
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "redirect:/logout";
        }
    }
        @PostMapping("/doTrain")
    public String doTrain(@RequestParam("jumperId") int theId){
        Jumper jumper = jumperRepository.findById(theId).orElse(null);
        jumper.makeTraining();
        jumperRepository.save(jumper);
        return "redirect:/jumper/training";
    }
}
