package com.example.housewareecommerce.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ComEvaController {

    @GetMapping("/get/comeva")
    public String getEvaluate(Model model) {
        model.addAttribute("content", "admin/admin-comeva");
        return "admin/AdminHome";
    }
}
