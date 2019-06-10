package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Coach;
import org.skijumping.skijumping.model.Team;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.CoachRepository;
import org.skijumping.skijumping.repository.TeamRepository;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.BindException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/teams")
public class AdminTeamController {

    @Autowired
    private TeamRepository teamRepository;
    private CoachRepository coachRepository;
    private UserRepository userRepository;
    public AdminTeamController(TeamRepository theTeamRepository, UserRepository userRepository, CoachRepository theCoachRepository){
        teamRepository=theTeamRepository;
        coachRepository=theCoachRepository;
        this.userRepository = userRepository;
    }

   @GetMapping("/")
    public String listTeams(Model theModel, Principal principal){
       User user = userRepository.findByUsername(principal.getName()).orElse(null);
       theModel.addAttribute("user",user);
       theModel.addAttribute("messages",teamRepository.findAll());
       return "admin/teams";
   }
    @GetMapping("/addTeam")
    public String addTeam(Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        theModel.addAttribute("user",user);
       Team team = new Team();
       theModel.addAttribute("team",team);
       theModel.addAttribute("coaches",findCoaches());
       return "admin/add-team";
    }
    @PostMapping("/updateTeam")
    public String updateTeam(@RequestParam("teamId") int theId,
                                    Model theModel) {

        Optional<Team> team = teamRepository.findById(theId);
        theModel.addAttribute("team", team);
        theModel.addAttribute("coaches",findCoaches());
        return "admin/add-team";
    }

    @PostMapping("/save")
    public String saveTeam(@Valid @ModelAttribute("team") Team team, BindingResult bindingResult, Model theModel){
        if (bindingResult.hasErrors()){
            theModel.addAttribute("coaches",findCoaches());
            return "admin/add-team";
        }
       teamRepository.save(team);
        return "redirect:/admin/teams/";
   }
    @PostMapping("/delete")
    public String delete(@RequestParam("teamId") int theId) {

        // delete the employee
        teamRepository.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/admin/teams/";

    }
    public List<Coach> findCoaches(){
        List <Coach> notWorkingCoaches = new ArrayList<>();
        List <Coach> coaches = (List<Coach>) coachRepository.findAll();
        for (Coach coach : coaches){
            if (coach.getTeam() == null)
                notWorkingCoaches.add(coach);
        }

        return notWorkingCoaches;
    }
 }
