package org.skijumping.skijumping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("home")
    public String homePage(Model theModel){
      //  theModel.addAttribute("theDate",new java.util.Date());
        return "index";
    }
}