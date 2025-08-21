package com.example.housewareecommerce.Controller.Client;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/homepage")
    public String userHome(Model model) {
        return "user/UserHome";
    }
}

