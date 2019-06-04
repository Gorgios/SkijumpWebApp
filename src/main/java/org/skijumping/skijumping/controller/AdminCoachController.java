package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Coach;
import org.skijumping.skijumping.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("admin/coaches")
public class AdminCoachController {

    private CoachRepository coachRepository;

    @Autowired
    public AdminCoachController(CoachRepository theCoachRepository){
        coachRepository=theCoachRepository;
    }

   @GetMapping("/")
    public String listCoaches(Model theModel){
       theModel.addAttribute("messages",coachRepository.findAll());
       return "admin/coaches";
   }
    @GetMapping("/addCoach")
    public String addCoach(Model theModel) {

        Coach coach = new Coach();
        theModel.addAttribute("coach",coach);
        return "admin/add-coach";
    }
    @PostMapping("/updateCoach")
    public String updateCoach(@RequestParam("coachId") int theId,
                                    Model theModel) {

        Optional<Coach> coach = coachRepository.findById(theId);
        theModel.addAttribute("coach", coach);
        return "admin/add-coach";
    }

    @PostMapping("/save")
    public String saveCoach(@ModelAttribute("coach") Coach coach){
        coachRepository.save(coach);
        return "redirect:/admin/coaches/";
   }
    @PostMapping("/delete")
    public String delete(@RequestParam("coachId") int theId) {

        // delete the employee
        coachRepository.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/admin/coaches/";

    }
}
