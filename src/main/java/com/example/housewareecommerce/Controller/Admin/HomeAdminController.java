package com.example.housewareecommerce.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAdminController {
    @GetMapping("/admin/homepage")
    public String HomeAdminPage(Model model){
        model.addAttribute("content", "admin/order-management");
        return "/admin/AdminHome";
    }
}
