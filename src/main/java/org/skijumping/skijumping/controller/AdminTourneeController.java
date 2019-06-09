package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Team;
import org.skijumping.skijumping.model.Tournee;
import org.skijumping.skijumping.repository.TourneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/tournees")
public class AdminTourneeController {

    private TourneeRepository tourneeRepository;

    @Autowired
    public AdminTourneeController(TourneeRepository tourneeRepository) {
        this.tourneeRepository = tourneeRepository;
    }
    @GetMapping("/")
    public String listTourneess(Model theModel){
        theModel.addAttribute("tournees",tourneeRepository.findAll());
        return "admin/tournees";
    }
    @GetMapping("/addTournee")
    public String addTournee(Model theModel) {

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
