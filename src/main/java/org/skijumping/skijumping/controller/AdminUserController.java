package org.skijumping.skijumping.controller;

import org.skijumping.skijumping.model.Coach;
import org.skijumping.skijumping.model.Role;
import org.skijumping.skijumping.model.User;
import org.skijumping.skijumping.repository.CoachRepository;
import org.skijumping.skijumping.repository.RoleRepository;
import org.skijumping.skijumping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("admin/users")
public class AdminUserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
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
        theModel.addAttribute("roles", roleRepository.findAll());
        return "admin/registration";
    }
    @PostMapping("/save")
    public String saveTeam(@ModelAttribute("user") User user){
        user.setEnabled((byte)1);
        Role role = new Role();
        role.setRole("USER");
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/admin/users/";
    }
    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int theId,
                             Model theModel){
        Optional<User> user = userRepository.findById(theId);
        theModel.addAttribute("roles", roleRepository.findAll());
        theModel.addAttribute("user",user);
        return "admin/users";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("userId") int theId) {

        // delete the employee
        userRepository.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/admin/users/";

    }
}
