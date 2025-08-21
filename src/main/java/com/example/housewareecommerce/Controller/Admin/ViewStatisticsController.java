package com.example.housewareecommerce.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ViewStatisticsController {

    @GetMapping("/get/statistics")
    public String productList(Model model) {
        return "admin/admin-statistics";
    }
}
