package com.example.housewareecommerce.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminCommentController {

    @GetMapping("/get/comment")
    public String getEvaluate(Model model) {
        model.addAttribute("content", "admin/admin-comment");
        return "admin/AdminHome";
    }
}
