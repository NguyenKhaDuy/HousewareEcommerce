package com.example.housewareecommerce.Controller;

import com.example.housewareecommerce.Model.DTO.CategoryDTO;
import com.example.housewareecommerce.Model.DTO.UserDTO;
import com.example.housewareecommerce.Service.CategoryService;
import com.example.housewareecommerce.Service.EmailService;
import com.example.housewareecommerce.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("message", "");
        return "LogAccount";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              Model model,
                              HttpSession session) {
        boolean ok = userService.login(email, password);
        if(ok){
            Optional<UserDTO> userDTO = Optional.of(new UserDTO());
            userDTO = userService.getUserByEmail(email);
            session.setAttribute("username", userDTO.get().getName());
            session.setAttribute("useremail", userDTO.get().getEmail());
            session.setAttribute("userid", userDTO.get().getId());

            List<CategoryDTO> categories = categoryService.getAll();
            model.addAttribute("categories", categories);
            model.addAttribute("showProducts", false);
            return "UserHome";
        }else{
            model.addAttribute("message","Email hoặc mật khẩu không đúng!");
        }
        return "LogAccount";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/account/login";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra email đã tồn tại
            if (userService.isEmailExists(userDTO.getEmail())) {
                response.put("success", false);
                response.put("message", "Email đã được sử dụng!");
                return ResponseEntity.badRequest().body(response);
            }

            // Tạo mã OTP 6 số
            String otpCode = String.format("%06d", new Random().nextInt(999999));

            // Lưu thông tin user và OTP vào session
            session.setAttribute("pendingUser", userDTO);
            session.setAttribute("otpCode", otpCode);
            session.setAttribute("otpExpiry", System.currentTimeMillis() + 300000); // 5 phút

            // Gửi email OTP
            String emailContent = String.format(
                    "Xin chào %s,\n\n" +
                            "Mã xác thực OTP của bạn là: %s\n" +
                            "Mã này có hiệu lực trong 5 phút.\n\n" +
                            "Trân trọng,\n" +
                            "Houseware Shop",
                    userDTO.getName(), otpCode
            );

            emailService.sendEmail(userDTO.getEmail(), "Mã xác thực OTP - Houseware Shop", emailContent);

            response.put("success", true);
            response.put("message", "Mã OTP đã được gửi về email của bạn!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/verify-otp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyOTP(@RequestParam String otpCode, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            String sessionOTP = (String) session.getAttribute("otpCode");
            Long otpExpiry = (Long) session.getAttribute("otpExpiry");
            UserDTO pendingUser = (UserDTO) session.getAttribute("pendingUser");

            if (sessionOTP == null || otpExpiry == null || pendingUser == null) {
                response.put("success", false);
                response.put("message", "Phiên làm việc đã hết hạn!");
                return ResponseEntity.badRequest().body(response);
            }

            if (System.currentTimeMillis() > otpExpiry) {
                response.put("success", false);
                response.put("message", "Mã OTP đã hết hạn!");
                return ResponseEntity.badRequest().body(response);
            }

            if (!sessionOTP.equals(otpCode)) {
                response.put("success", false);
                response.put("message", "Mã OTP không đúng!");
                return ResponseEntity.badRequest().body(response);
            }

            // Tạo user
            boolean created = userService.createUser(pendingUser);

            if (created) {
                // Xóa thông tin khỏi session
                session.removeAttribute("pendingUser");
                session.removeAttribute("otpCode");
                session.removeAttribute("otpExpiry");

                response.put("success", true);
                response.put("message", "Đăng ký thành công!");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Không thể tạo tài khoản!");
                return ResponseEntity.internalServerError().body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/resend-otp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> resendOTP(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            UserDTO pendingUser = (UserDTO) session.getAttribute("pendingUser");

            if (pendingUser == null) {
                response.put("success", false);
                response.put("message", "Phiên làm việc đã hết hạn!");
                return ResponseEntity.badRequest().body(response);
            }

            // Tạo mã OTP mới
            String otpCode = String.format("%06d", new Random().nextInt(999999));

            // Cập nhật session
            session.setAttribute("otpCode", otpCode);
            session.setAttribute("otpExpiry", System.currentTimeMillis() + 300000);

            // Gửi email OTP mới
            String emailContent = String.format(
                    "Xin chào %s,\n\n" +
                            "Mã xác thực OTP mới của bạn là: %s\n" +
                            "Mã này có hiệu lực trong 5 phút.\n\n" +
                            "Trân trọng,\n" +
                            "Houseware Shop",
                    pendingUser.getName(), otpCode
            );

            emailService.sendEmail(pendingUser.getEmail(), "Mã xác thực OTP mới - Houseware Shop", emailContent);

            response.put("success", true);
            response.put("message", "Mã OTP mới đã được gửi!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/forgot-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra email có tồn tại không
            if (!userService.isEmailExists(email)) {
                response.put("success", false);
                response.put("message", "Email không tồn tại trong hệ thống!");
                return ResponseEntity.badRequest().body(response);
            }

            // Tạo mật khẩu mới (8 ký tự ngẫu nhiên)
            String newPassword = generateRandomPassword();

            // Cập nhật mật khẩu trong database
            boolean updated = userService.updatePassword(email, newPassword);

            if (!updated) {
                response.put("success", false);
                response.put("message", "Không thể cập nhật mật khẩu!");
                return ResponseEntity.internalServerError().body(response);
            }

            // Gửi email với mật khẩu mới
            String emailContent = String.format(
                    "Xin chào,\n\n" +
                            "Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản: %s\n\n" +
                            "Mật khẩu mới của bạn là: %s\n\n" +
                            "Vui lòng đăng nhập và đổi mật khẩu ngay sau khi truy cập hệ thống.\n\n" +
                            "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng liên hệ với chúng tôi ngay lập tức.\n\n" +
                            "Trân trọng,\n" +
                            "Houseware Shop",
                    email, newPassword
            );

            emailService.sendEmail(email, "Đặt lại mật khẩu Shop", emailContent);

            response.put("success", true);
            response.put("message", "Mật khẩu mới đã được gửi về email của bạn!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }
}