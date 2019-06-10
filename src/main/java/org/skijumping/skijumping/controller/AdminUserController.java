package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.*;
import org.skijumping.skijumping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("admin/users")
public class AdminUserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JumperRepository jumperRepository;
    private TeamRepository teamRepository;
    private TourneeRepository tourneeRepository;
    private ClasificationRepository clasificationRepository;
    @Autowired
    public AdminUserController(RoleRepository roleRepository, UserRepository userRepository, JumperRepository jumperRepository,
                               TourneeRepository tourneeRepository,TeamRepository teamRepository, ClasificationRepository clasificationRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jumperRepository = jumperRepository;
        this.teamRepository = teamRepository;
        this.tourneeRepository = tourneeRepository;
        this.clasificationRepository = clasificationRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String listUsers(Model theModel){
       theModel.addAttribute("messages",userRepository.findAll());
       theModel.addAttribute("roles", roleRepository.findAll());
       return "admin/users";
   }
    @GetMapping("/addUser")
    public String addUser(Model theModel){
        User user = new User();
        theModel.addAttribute("roles",roleRepository.findAll());
        theModel.addAttribute("user",user);
        return "admin/registration";
    }
    @PostMapping("/save")
    public String saveTeam(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model theModel){
        if (bindingResult.hasErrors()){
            theModel.addAttribute("roles",roleRepository);
            return "admin/registration";
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled((byte) 1);
            userRepository.save(user);

            return "redirect:";
        }
    }
    @PostMapping("/updateRole")
    public String updateUser(@Valid  @ModelAttribute("user") User user, BindingResult bindingResult, Model theModel){
        if (bindingResult.hasErrors()){
            theModel.addAttribute("roles", roleRepository.findAll());
            theModel.addAttribute("users",userRepository.findAll());
            return "admin/users";
        }
        else {
         //   System.out.println(user.getId() + "\n\n\n\n\n\n\n\n");
            userRepository.save(user);
            return "redirect:/admin/users/";
        }
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("userId") int theId) {

        // delete the employee
        userRepository.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/admin/users/";

    }
}
