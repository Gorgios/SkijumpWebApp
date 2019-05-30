package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Coach;
import org.skijumping.skijumping.model.Role;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.CoachRepository;
import org.skijumping.skijumping.repository.RoleRepository;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("admin/roles")
public class AdminUserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public AdminUserController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String listUsers(Model theModel){
       theModel.addAttribute("messages",userRepository.findAll());
       return "admin/users";
   }
    @GetMapping("/addUser")
    public String addUser(Model theModel){
        User user = new User();
        theModel.addAttribute("user",user);
        return "registration";
    }
    @PostMapping("/save")
    public String saveTeam(@ModelAttribute("user") User user){
        user.setEnabled((byte)1);
        Role role = new Role();
        role.setRole("USER");
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/admin/teams/";
    }
    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int theId,
                             Model theModel){
        Optional<User> user = userRepository.findById(theId);
        theModel.addAttribute("roles", roleRepository.findAll());
        theModel.addAttribute("user",user);
        return "admin/edit-user";
    }
}
