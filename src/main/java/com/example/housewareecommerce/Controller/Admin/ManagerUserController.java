package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.UserDTO;
import com.example.housewareecommerce.Service.StatusService;
import com.example.housewareecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/user")
public class ManagerUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @GetMapping("/getAll")
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<UserEntity> userPage = userService.getAllUsers(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

        model.addAttribute("content", "admin/user-list");
        return "/admin/AdminHome";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO<Void>> deleteUser(@PathVariable Long id) {
        MessageDTO<Void> response = new MessageDTO<>();
        try {
            boolean deleted = userService.deleteUserById(id);
            if (deleted) {
                response.setMessage("Xóa người dùng thành công");
                response.setHttpStatus(HttpStatus.OK);
            } else {
                response.setMessage("Không tìm thấy người dùng");
                response.setHttpStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMessage("Lỗi khi xóa người dùng: " + e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        try {
            Optional<UserEntity> userOpt = userService.getUserById(id);
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();

                List<StatusEntity> statuses = statusService.findByIdIn(Arrays.asList(37L, 38L));

                model.addAttribute("user", user);
                model.addAttribute("statuses", statuses);
                model.addAttribute("content", "admin/user-edit");
                return "/admin/AdminHome";
            }
        } catch (Exception e) {
            System.err.println("Error finding user: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/admin/user/getAll?message=Không%20tìm%20thấy%20người%20dùng&type=error";
    }

    // Chỉnh sửa user
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email,
                             @RequestParam(value = "address", required = false) String address,
                             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                             @RequestParam(value = "gender", required = false) String gender,
                             @RequestParam(value = "statusId", required = false) Long statusId,
                             RedirectAttributes redirectAttributes) {
        try {
            UserDTO updatedUser = new UserDTO();
            updatedUser.setName(name);
            updatedUser.setEmail(email);
            updatedUser.setAddress(address);
            updatedUser.setPhoneNumber(phoneNumber);
            updatedUser.setGender(gender);
            updatedUser.setStatusId(statusId);

            userService.updateUserById(id, updatedUser);
            redirectAttributes.addAttribute("message", "Cập nhật người dùng thành công");
            redirectAttributes.addAttribute("type", "success");
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addAttribute("message", "Lỗi khi cập nhật người dùng: " + e.getMessage());
            redirectAttributes.addAttribute("type", "error");
        }
        return "redirect:/admin/user/getAll";
    }
}