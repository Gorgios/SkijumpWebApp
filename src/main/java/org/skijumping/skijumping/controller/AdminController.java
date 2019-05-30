package org.skijumping.skijumping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/")
    public String homePage(Model theModel){
      //  theModel.addAttribute("theDate",new java.util.Date());
        return "admin/index";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}