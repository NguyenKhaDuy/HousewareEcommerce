package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.UserDTO;
import com.example.housewareecommerce.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile/edit")
    public String showProfileEditForm(HttpSession session, Model model) {
        try {
            Long userId = (Long) session.getAttribute("userid");
            if (userId == null) {
                return "";
            }

            Optional<UserEntity> userOpt = userService.getUserById(userId);
            if (userOpt.isPresent()) {
                model.addAttribute("user", userOpt.get());
                return "user/profile-edit";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam(value = "address", required = false) String address,
                                @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                @RequestParam(value = "gender", required = false) String gender,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userid");
            if (userId == null) {
                return "";
            }

            UserDTO updatedUser = new UserDTO();
            updatedUser.setName(name);
            updatedUser.setEmail(email);
            updatedUser.setAddress(address);
            updatedUser.setPhoneNumber(phoneNumber);
            updatedUser.setGender(gender);

            userService.updateUserById(userId, updatedUser);

            redirectAttributes.addAttribute("message", "Cập nhật tài khoản thành công");
            redirectAttributes.addAttribute("type", "success");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", "Lỗi khi cập nhật: " + e.getMessage());
            redirectAttributes.addAttribute("type", "error");
        }
        return "";
    }

}
