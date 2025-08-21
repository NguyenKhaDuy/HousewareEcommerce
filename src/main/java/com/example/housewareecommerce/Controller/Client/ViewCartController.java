package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Model.DTO.CartDTO;
import com.example.housewareecommerce.Model.DTO.CartItemDTO;
import com.example.housewareecommerce.Service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
@RequestMapping("/view")
public class ViewCartController {

    private static final Logger logger = Logger.getLogger(ViewCartController.class.getName());

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public ModelAndView getCart(HttpSession session) {
        ModelAndView mav = new ModelAndView("user/cart-page");
        Long userId = (Long) session.getAttribute("userid"); // Lấy userId từ session

        if (userId != null) {
            CartDTO cartDTO = cartService.getCartByUser(userId);
            if (cartDTO != null) {
                mav.addObject("cartDto", cartDTO);
                // Tính tổng giá trị giỏ hàng
                double totalAmount = cartDTO.getCartItemDTOS().stream()
                        .mapToDouble(CartItemDTO::getSubTotal)
                        .sum();
                mav.addObject("totalAmount", totalAmount);
            } else {
                // Giỏ hàng trống
                mav.addObject("cartDto", null);
            }
        } else {
            // Chưa đăng nhập, chuyển hướng đến trang login
            mav.setViewName("redirect:/account/login");
        }

        return mav;
    }
}
