package com.example.housewareecommerce.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ViewProductController {

    @GetMapping("/products")
    public String productList(Model model) {
        model.addAttribute("content", "admin/productList");
        return "AdminHome";
    }

    @GetMapping("/products/{id}/images")
    public String productImages(@PathVariable("id") Long id, Model model){
        model.addAttribute("content", "admin/productImages :: content");
        model.addAttribute("productId", id);
        return "AdminHome";
    }
}
