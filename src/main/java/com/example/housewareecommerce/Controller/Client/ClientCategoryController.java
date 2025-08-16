package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/category")
public class ClientCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAll")
    public String home(Model model) {
        try {
            List<CategoryDTO> categories = categoryService.getAll();
            model.addAttribute("categories", categories);
            model.addAttribute("showProducts", false);
            return "UserHome";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải danh mục sản phẩm");
            return "error";
        }
    }
}
