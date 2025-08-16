package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.CartDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.CartRequest;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartDTO getCartByUser(Long userId);
    MessageDTO addToCart(CartRequest cartRequest);
    MessageDTO deleteProductInCart(CartRequest cartRequest);
}
