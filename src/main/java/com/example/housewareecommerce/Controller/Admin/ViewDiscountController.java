package com.example.housewareecommerce.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ViewDiscountController {

    @GetMapping("/view/discount")
    public String showDiscountPage(Model model) {
        model.addAttribute("content", "admin/discount-management");
        return "AdminHome";
    }
}
