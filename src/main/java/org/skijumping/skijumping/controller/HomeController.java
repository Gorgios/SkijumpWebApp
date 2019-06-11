package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.*;
import org.skijumping.skijumping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    private RoleRepository roleRepository;

    @Autowired
    public HomeController(ClasificationRepository clasificationRepository, CompetitionRepository competitionRepository,
                          HillRepository hillRepository, TourneeRepository tourneeRepository,
                          StartRepository startRepository, JumperRepository jumperRepository, UserRepository userRepository,
                          CoachRepository coachRepository, TeamRepository teamRepository, RoleRepository roleRepository) {
        this.clasificationRepository = clasificationRepository;
        this.competitionRepository = competitionRepository;
        this.hillRepository = hillRepository;
        this.tourneeRepository = tourneeRepository;
        this.startRepository = startRepository;
        this.jumperRepository = jumperRepository;
        this.userRepository = userRepository;
        this.coachRepository = coachRepository;
        this.teamRepository = teamRepository;
        this.roleRepository = roleRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String startPage(Model theModel){
        theModel.addAttribute("competitions",competitionRepository.findAll());
        List<Clasification> clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(7).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("worldCup",clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(8).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("tcs",  clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(9).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("w5",  clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(10).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("rawAir",  clas);
        clas = clasificationRepository
                .findAllByTournee(tourneeRepository.findById(11).orElse(null));
        Collections.sort(clas, Collections.reverseOrder());
        theModel.addAttribute("planica7",  clas);
        return "/index";
    }
    @RequestMapping("/login")
    public String login(Model theModel) {

        User user = new User();
        Jumper jumper = new Jumper();
        theModel.addAttribute("user",user);
        theModel.addAttribute("jumper",jumper);
        theModel.addAttribute("teams", teamRepository.findAll());
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
    @PostMapping("/save")
    public String saveTeam(@Valid @ModelAttribute("user") User user,BindingResult bindingResult, @ModelAttribute("jumper") Jumper jumper, Model theModel){
        if (bindingResult.hasErrors()){
            theModel.addAttribute("teams", teamRepository.findAll());
            return "login";
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled((byte) 1);
            Role role = roleRepository.findById(1).orElse(null);
            user.setRole(role);
            jumper.setUser(user);
            jumper.setCredits(100);
            userRepository.save(user);

            for (Tournee t : tourneeRepository.findAll()) {
                Clasification clasification = new Clasification();
                clasification.setJumper(jumper);
                clasification.setTournee(t);
                clasification.setPoints(0);
                clasificationRepository.save(clasification);
            }
            return "redirect:/login";
        }
    }
}
