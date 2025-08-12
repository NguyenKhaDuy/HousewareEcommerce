package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Entity.UserEntity;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.UserDTO;
import com.example.housewareecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("content", "user/user-list");
        return "AdminHome";
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

//    // Chỉnh sửa user
//    @PutMapping("edit/{id}")
//    public ResponseEntity<MessageDTO<UserEntity>> updateUser(
//            @PathVariable Long id,
//            @RequestBody UserDTO updatedUser) {
//
//        MessageDTO<UserEntity> response = new MessageDTO<>();
//        try {
//            UserEntity user = userService.updateUserById(id, updatedUser);
//            if (user != null) {
//                response.setMessage("Cập nhật người dùng thành công");
//                response.setHttpStatus(HttpStatus.OK);
//                response.setData(user);
//            } else {
//                response.setMessage("Không tìm thấy người dùng");
//                response.setHttpStatus(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            response.setMessage("Lỗi khi cập nhật người dùng: " + e.getMessage());
//            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(response, response.getHttpStatus());
//    }
}
