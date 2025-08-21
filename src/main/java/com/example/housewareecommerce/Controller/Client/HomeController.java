package com.example.housewareecommerce.Controller.Client;


import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/homepage")
    public String userHome(Model model) {
        try {
            List<CategoryDTO> categories = categoryService.getAll();
            model.addAttribute("categories", categories);
            model.addAttribute("showProducts", false);
            return "/user/UserHome";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải danh mục sản phẩm");
            return "error";
        }
    }
}

