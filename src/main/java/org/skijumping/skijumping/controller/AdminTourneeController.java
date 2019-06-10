package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Team;
import org.skijumping.skijumping.model.Tournee;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.TourneeRepository;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin/tournees")
public class AdminTourneeController {

    private TourneeRepository tourneeRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminTourneeController(TourneeRepository tourneeRepository, UserRepository userRepository) {
        this.tourneeRepository = tourneeRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/")
    public String listTourneess(Model theModel, Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
        theModel.addAttribute("tournees",tourneeRepository.findAll());
        return "admin/tournees";
    }
    @GetMapping("/addTournee")
    public String addTournee(Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);

       Tournee tournee = new Tournee();
       theModel.addAttribute("tournee",tournee);
        return "admin/add-tournee";
    }
    @PostMapping("/updateTournee")
    public String updateTournee(@RequestParam("tourneeId") int theId,
                             Model theModel) {

        Optional<Tournee> tournee = tourneeRepository.findById(theId);
        theModel.addAttribute("tournee", tournee);
        return "admin/add-tournee";
    }

    @PostMapping("/save")
    public String saveTournee(@Valid @ModelAttribute("tournee") Tournee tournee, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "admin/add-tournee";
        }
        else {
            tourneeRepository.save(tournee);
            return "redirect:/admin/tournees/";
        }
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("tourneeId") int theId) {

        // delete the employee
        tourneeRepository.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/admin/tournees/";

    }

}
