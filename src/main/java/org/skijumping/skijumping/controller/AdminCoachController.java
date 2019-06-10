package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Coach;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.CoachRepository;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("admin/coaches")
public class AdminCoachController {

    private UserRepository userRepository;
    private CoachRepository coachRepository;

    @Autowired
    public AdminCoachController(CoachRepository theCoachRepository, UserRepository userRepository){
        this.userRepository = userRepository;
        coachRepository=theCoachRepository;
    }

   @GetMapping("/")
    public String listCoaches(Model theModel, Principal principal){
       User user = userRepository.findByUsername(principal.getName());
       theModel.addAttribute("user",user);
       theModel.addAttribute("messages",coachRepository.findAll());
       return "admin/coaches";
   }
    @GetMapping("/addCoach")
    public String addCoach(Model theModel, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        theModel.addAttribute("user",user);
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
    public String saveCoach(@Valid  @ModelAttribute("coach") Coach coach, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "admin/add-coach";
        }
        else {
            coachRepository.save(coach);
            return "redirect:/admin/coaches/";
        }
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("coachId") int theId) {

        // delete the employee
        coachRepository.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/admin/coaches/";

    }
}
