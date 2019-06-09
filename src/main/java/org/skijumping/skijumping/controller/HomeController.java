package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Clasification;
import org.skijumping.skijumping.model.Competition;
import org.skijumping.skijumping.model.Start;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {


    private ClasificationRepository clasificationRepository;
    private CompetitionRepository competitionRepository;
    private HillRepository hillRepository;
    private TourneeRepository tourneeRepository;
    private StartRepository startRepository;
    private JumperRepository jumperRepository;
    private UserRepository userRepository;
    private CoachRepository coachRepository;
    private TeamRepository teamRepository;

    @Autowired
    public HomeController(ClasificationRepository clasificationRepository, CompetitionRepository competitionRepository,
                          HillRepository hillRepository, TourneeRepository tourneeRepository,
                          StartRepository startRepository, JumperRepository jumperRepository, UserRepository userRepository,
                          CoachRepository coachRepository, TeamRepository teamRepository) {
        this.clasificationRepository = clasificationRepository;
        this.competitionRepository = competitionRepository;
        this.hillRepository = hillRepository;
        this.tourneeRepository = tourneeRepository;
        this.startRepository = startRepository;
        this.jumperRepository = jumperRepository;
        this.userRepository = userRepository;
        this.coachRepository = coachRepository;
        this.teamRepository = teamRepository;
    }


    @RequestMapping("/")
    public String startPage(Model theModel){
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
        return "/index";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/403")
    public String get403Page() {
        return "error/403";
    }
    @GetMapping("/loginSuccess")
    public String successLogin( Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:/admin/";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("JUMPER"))) {
            return "redirect:/jumper/";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("FIS"))) {
            return "redirect:/fis/";
        }
        return "redirect:/";
    }
    @GetMapping("/loginError")
    public ModelAndView loginError(ModelAndView mav, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginError", true);
        mav.setViewName("redirect:/");
        return mav;
    }
    @RequestMapping("/showCompetition")
    public String showCompetition(@RequestParam("competitionId") int theId,
                                  Model theModel, Principal principal) {

        Competition competition = competitionRepository.findById(theId).orElse(null);

        List<Start> starts = competition.getStarts();
        Collections.sort(starts,Collections.reverseOrder());
        theModel.addAttribute("starts",starts);
        return "competition-single";
    }
}
