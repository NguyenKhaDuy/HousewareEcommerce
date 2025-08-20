package com.example.housewareecommerce.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ViewOrderController {

    @GetMapping("/view/order")
    public String categoryManagementPage(Model model) {
        model.addAttribute("content", "admin/order-management");
        return "AdminHome";
    }
}
