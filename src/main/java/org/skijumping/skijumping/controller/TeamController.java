package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.repository.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamController  {

    private TeamRepository teamRepository;
    @GetMapping("/list")
    public String showList(Model model){
        model.addAttribute("teams", teamRepository.findAll());
        return "list-teams";
    }
    @RequestMapping("/add")
    public String addTeam(){
        return "add-team";
    }

}
